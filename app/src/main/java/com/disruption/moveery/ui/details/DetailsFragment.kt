package com.disruption.moveery.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentDetailsBinding
import com.disruption.moveery.utils.Constants
import com.disruption.moveery.utils.DetailsHelper

/**
 * A fragment to show the details of the movie
 */
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)

        displayMovieDetails()

        // Inflate the layout for this fragment
        return binding.root
    }

    /**Display the passed in movie from the [args]*/
    private fun displayMovieDetails() {
        val movie = args.movie
        val posterUrl = Constants.IMAGE_BASE_URL + movie.poster_path
        Glide.with(requireContext())
            .load(posterUrl)
            .centerCrop()
            .placeholder(R.drawable.movie_loading_animation)
            .into(binding.ivMoviePoster)

        binding.tvMovieTitle.text = movie.original_title
        binding.tvMovieGenre.text = DetailsHelper.getGenres(movie.genre_ids)
        binding.tvMovieYear.text = movie.release_date
        binding.tvMovieOverview.text = movie.overview
        binding.tvMovieRating.text = (movie.vote_average * 10).toString().plus("%")
    }
}
