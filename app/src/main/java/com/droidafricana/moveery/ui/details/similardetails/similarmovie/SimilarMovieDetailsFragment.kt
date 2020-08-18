package com.droidafricana.moveery.ui.details.similardetails.similarmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentSimilarMovieDetailsBinding

/**
 *Display similar show details excluding the "Similar" section
 */
class SimilarMovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSimilarMovieDetailsBinding
    private lateinit var viewModel: SimilarMovieDetailsViewModel

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
        viewModel = ViewModelProvider(this).get(SimilarMovieDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
