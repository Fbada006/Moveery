package com.droidafricana.moveery.ui.details.similardetails.similarmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentSimilarMovieDetailsBinding
import com.droidafricana.moveery.di.Injectable
import timber.log.Timber

/**
 *Display similar show details excluding the "Similar" section
 */
class SimilarMovieDetailsFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentSimilarMovieDetailsBinding
    private val viewModel: SimilarMovieDetailsViewModel by viewModels()
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
        // TODO: Use the ViewModel
        val movie = args.similarMovie
        Timber.e("Movie title similar is ------------ ${movie.title}")
    }

}
