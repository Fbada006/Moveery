package com.disruption.moveery.ui.details.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentMovieDetailsBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.videos.Video
import com.disruption.moveery.ui.details.VideoAdapter
import com.disruption.moveery.utils.*
import com.disruption.moveery.utils.Resource.Status.*
import com.like.LikeButton
import com.like.OnLikeListener
import javax.inject.Inject

/**
 * A fragment to show the details of the movie
 */
class MovieDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var requestManager: RequestManager

    private lateinit var binding: FragmentMovieDetailsBinding
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<MovieDetailsViewModel> { viewModelFactory }
    private lateinit var video: Video

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val similarAdapter =
            MovieSimilarPagedAdapter(
                requireContext(),
                OnMovieClickListener {
                    //Do nothing for now
                })

        val videoAdapter = VideoAdapter(
            requireContext(),
            OnVideoClickListener { playVideo(it) }
        )

        val movie = args.movie

        if (movie != null) {
            displayMovieDetails(movie)
            viewModel.getSimilarMovies(movie.id)
            viewModel.getVideosResource(movie.id)
        }

        binding.similarMoviesList.adapter = similarAdapter
        binding.videoMoviesList.adapter = videoAdapter

        val similarLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val videoLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.similarMoviesList.layoutManager = similarLayoutManager
        binding.videoMoviesList.layoutManager = videoLayoutManager

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) binding.similarMoviesError.visibility = View.VISIBLE else View.GONE
            similarAdapter.submitList(it)
        })

        movie?.id?.let {
            viewModel.videoRes.observe(viewLifecycleOwner, Observer { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        if (!resource.data.isNullOrEmpty()) {
                            videoAdapter.submitList(resource.data)
                        } else {
                            showError()
                        }
                    }
                    ERROR -> showError()
                    LOADING -> showLoading()
                }
            })
        }

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.similarMoviesList)

        observeLikedState()
        onLikeButtonClicked()
        onShareFabClicked()

        binding.toolbar.showAndHandleBackButton(activity)

        binding.movieDetailsViewModel = viewModel
    }

    private fun onShareFabClicked() {
        binding.shareMovie.setOnClickListener { buildMovieShareIntent(args.movie!!.id) }
    }

    private fun observeLikedState() {
        viewModel.isMovieInFav(args.movie?.id!!).observe(viewLifecycleOwner, Observer {
            binding.likeButton.isLiked = it != null
        })
    }

    private fun onLikeButtonClicked() {
        binding.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                args.movie?.let { viewModel.insertMovieIntoFav(it) }
            }

            override fun unLiked(likeButton: LikeButton?) {
                args.movie?.let { viewModel.deleteMoveFromFav(it.id) }
            }
        })
    }

    /**Display the passed in movie from the [args]*/
    private fun displayMovieDetails(movie: Movie?) {
        val posterUrl = Constants.IMAGE_BASE_URL + movie?.poster_path

        binding.ivMoviePoster.loadImage(posterUrl, requestManager)

        binding.tvMovieTitle.text = movie?.original_title
        binding.tvMovieGenre.text = DetailsHelper.getGenres(movie?.genre_ids, requireContext())
        binding.tvMovieYear.text = movie?.release_date?.substring(0, 4)
        binding.tvMovieOverview.text = movie?.overview
        binding.tvMovieRating.text = movie?.vote_average?.toPercentage()
        binding.ratingCustomView.apply {
            setValue(movie?.vote_average?.toInt()!!.times(10))
            setFillColor(
                DetailsHelper.getRatingColor(
                    movie.vote_average.toInt().times(10),
                    requireContext()
                )
            )
            setStrokeColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        }
    }

    private fun showLoading() {
        binding.videoLoadingSpinner.visibility = View.VISIBLE
        binding.videoMoviesError.visibility = View.GONE
    }

    private fun showError() {
        binding.videoLoadingSpinner.visibility = View.GONE
        binding.videoMoviesError.visibility = View.VISIBLE
    }
}
