package com.disruption.moveery.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentDetailsBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.models.movie.Movie
import com.disruption.moveery.models.altmovie.AltMovie
import com.disruption.moveery.utils.Constants
import com.disruption.moveery.utils.DetailsHelper
import javax.inject.Inject

/**
 * A fragment to show the details of the movie
 */
class DetailsFragment : Fragment(), Injectable {
    val TAG = "DetailsFragment"
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)

        val movie = args.movie
        val searchedMovie = args.altMovie
        if (movie != null) displayMovieDetails(movie)
        if (searchedMovie != null) displaySearchedMovieDetails(searchedMovie)

        showAndHandleBackButton()

        // Inflate the layout for this fragment
        return binding.root
    }

    /**Display the passed in movie from the [args]*/
    private fun displayMovieDetails(movie: Movie?) {
        val posterUrl = Constants.IMAGE_BASE_URL + movie?.poster_path

        setMoviePoster(posterUrl)

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

    /**Display the passed in movie from the [args]*/
    private fun displaySearchedMovieDetails(movie: AltMovie?) {
        val posterUrl = Constants.IMAGE_BASE_URL + movie?.poster_path

        setMoviePoster(posterUrl)

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

    private fun setMoviePoster(posterUrl: String) {
        requestManager
            .load(posterUrl)
            .into(binding.ivMoviePoster)
    }

    private fun showAndHandleBackButton() {
        val toolbar = binding.toolbar
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

//    private fun setStatusBarColorFromPoster(posterBitmap: Bitmap) {
//        Palette.from(posterBitmap).generate { palette ->
//            val darkVibrantColor = palette?.getDarkVibrantColor(
//                ContextCompat.getColor(
//                    requireContext(),
//                    R.color.colorPrimaryDark
//                )
//            )
//
//            activity?.window?.statusBarColor =
//                ContextCompat.getColor(requireContext(), darkVibrantColor!!)
//        }
//    }
}
