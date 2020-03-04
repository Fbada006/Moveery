package com.disruption.moveery.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.MainActivity
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentDetailsBinding
import com.disruption.moveery.utils.Constants
import com.disruption.moveery.utils.DetailsHelper
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.view.*
import javax.inject.Inject

/**
 * A fragment to show the details of the movie
 */
class DetailsFragment : DaggerFragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)

        displayMovieDetails()

        showAndHandleBackButton()

        //  activity!!.window.statusBarColor = Color.TRANSPARENT

        // Inflate the layout for this fragment
        return binding.root
    }

    /**Display the passed in movie from the [args]*/
    private fun displayMovieDetails() {
        val movie = args.movie
        val posterUrl = Constants.IMAGE_BASE_URL + movie.poster_path
//        Glide.with(requireContext())
//            .load(posterUrl)
//            .centerCrop()
//            .error(R.drawable.ic_broken_image)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(R.drawable.movie_loading_animation)
//            .into(binding.ivMoviePoster)

        requestManager
            .load(posterUrl)
            .into(binding.ivMoviePoster)

        val average = ((movie.vote_average)!! * 10).toInt()

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

    private fun showAndHandleBackButton() {
        val toolbar = binding.toolbar
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity?)!!.supportActionBar!!.show()
    }
}
