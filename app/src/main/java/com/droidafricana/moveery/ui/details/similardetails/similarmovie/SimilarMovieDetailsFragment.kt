package com.droidafricana.moveery.ui.details.similardetails.similarmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentSimilarMovieDetailsBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.ui.details.VideoAdapter
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.DetailsHelper
import com.droidafricana.moveery.utils.OnVideoClickListener
import com.droidafricana.moveery.utils.Resource
import com.droidafricana.moveery.utils.buildShareIntent
import com.droidafricana.moveery.utils.loadImage
import com.droidafricana.moveery.utils.playVideo
import com.droidafricana.moveery.utils.showAndHandleBackButton
import com.droidafricana.moveery.utils.toPercentage
import com.like.LikeButton
import com.like.OnLikeListener
import javax.inject.Inject

/**
 *Display similar show details excluding the "Similar" section
 */
class SimilarMovieDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var requestManager: RequestManager

    private lateinit var binding: FragmentSimilarMovieDetailsBinding
    private val viewModel: SimilarMovieDetailsViewModel by viewModels { viewModelFactory }
    private val args: SimilarMovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_similar_movie_details,
            container,
            false
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        val movie = args.similarMovie

        val videoAdapter = VideoAdapter(
            requireContext(),
            OnVideoClickListener { playVideo(it) }
        )

        if (movie != null) {
            displayMovieDetails(movie)
            viewModel.getVideosResource(movie.id)
        }

        binding.similarVideoMoviesList.adapter = videoAdapter

        val videoLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.similarVideoMoviesList.layoutManager = videoLayoutManager

        movie?.id?.let {
            viewModel.videoRes.observe(viewLifecycleOwner, Observer { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        if (!resource.data.isNullOrEmpty()) {
                            showData()
                            videoAdapter.submitList(resource.data)
                        } else {
                            showError()
                        }
                    }
                    Resource.Status.ERROR -> showError()
                    Resource.Status.LOADING -> showLoading()
                }
            })
        }

        observeLikedState()
        onLikeButtonClicked()
        onShareFabClicked()

        binding.toolbar.showAndHandleBackButton(activity)
    }

    private fun displayMovieDetails(movie: Movie?) {
        val posterUrl = Constants.IMAGE_BASE_URL + movie?.poster_path

        binding.ivSimilarMoviePoster.loadImage(posterUrl, requestManager)

        binding.tvSimilarMovieTitle.text = movie?.original_title
        binding.tvSimilarMovieGenre.text =
            DetailsHelper.getGenres(movie?.genre_ids, requireContext())
        binding.tvSimilarMovieYear.text = movie?.release_date?.substring(0, 4)
        binding.tvSimilarMovieOverview.text = movie?.overview
        binding.tvSimilarMovieRating.text = movie?.vote_average?.toPercentage()
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

    private fun onShareFabClicked() {
        binding.shareMovie.setOnClickListener {
            buildShareIntent(
                args.similarMovie!!.id,
                Constants.MOVIE_TYPE
            )
        }
    }

    private fun observeLikedState() {
        viewModel.isMovieInFav(args.similarMovie?.id!!).observe(viewLifecycleOwner, Observer {
            binding.likeButton.isLiked = it != null
        })
    }

    private fun onLikeButtonClicked() {
        binding.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                args.similarMovie?.let { viewModel.insertMovieIntoFav(it) }
            }

            override fun unLiked(likeButton: LikeButton?) {
                args.similarMovie?.let { viewModel.deleteMoveFromFav(it.id) }
            }
        })
    }

    private fun showLoading() {
        binding.videoLoadingSpinner.visibility = View.VISIBLE
        binding.similarVideoMoviesError.visibility = View.GONE
    }

    private fun showError() {
        binding.videoLoadingSpinner.visibility = View.GONE
        binding.similarVideoMoviesError.visibility = View.VISIBLE
    }

    private fun showData() {
        binding.videoLoadingSpinner.visibility = View.GONE
        binding.similarVideoMoviesError.visibility = View.GONE
    }
}
