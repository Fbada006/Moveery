package com.disruption.moveery.ui.details.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.disruption.moveery.utils.Constants.YOUTUBE_VIDEO_BASE_URL
import com.disruption.moveery.utils.Resource.Status.*
import timber.log.Timber
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

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
            similarAdapter.submitList(it)
        })

        movie?.id?.let {
            viewModel.videoRes.observe(viewLifecycleOwner, Observer { resource ->
                when (resource.status) {
                    SUCCESS -> videoAdapter.submitList(resource.data)
                    ERROR -> Timber.e("Error ------ ${resource.message}")
                    LOADING -> Timber.e("Loading ------ ")
                }
            })
        }

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.similarMoviesList)

        binding.toolbar.showAndHandleBackButton(activity)

        binding.movieDetailsViewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun playVideo(video: Video?) {
        val videoLink = String.format(YOUTUBE_VIDEO_BASE_URL, video?.key)
        val videoIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
        try {
            requireContext().startActivity(videoIntent)
        } catch (ex: Exception) {
            Timber.e("Error playing video ----------- $ex")
            Toast.makeText(context, getString(R.string.cannot_play_video), Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**Display the passed in movie from the [args]*/
    private fun displayMovieDetails(movie: Movie?) {
        val posterUrl = Constants.IMAGE_BASE_URL + movie?.poster_path

        binding.ivMoviePoster.loadImage(posterUrl, requestManager)

        val average = ((movie?.vote_average)!! * 10).toInt()

        binding.tvMovieTitle.text = movie.original_title
        binding.tvMovieGenre.text = DetailsHelper.getGenres(movie.genre_ids, requireContext())
        binding.tvMovieYear.text = movie.release_date?.substring(0, 4)
        binding.tvMovieOverview.text = movie.overview
        binding.tvMovieRating.text = average.toString().plus("%")
        binding.ratingCustomView.apply {
            setValue(average)
            setFillColor(DetailsHelper.getRatingColor(average, requireContext()))
            setStrokeColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        }
    }
}
