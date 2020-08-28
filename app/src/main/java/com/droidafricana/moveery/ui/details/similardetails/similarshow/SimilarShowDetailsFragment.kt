package com.droidafricana.moveery.ui.details.similardetails.similarshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentSimilarShowDetailsBinding
import com.droidafricana.moveery.di.Injectable
import timber.log.Timber
import javax.inject.Inject

/**
 *Display similar movie details excluding the "Similar" section
 */
class SimilarShowDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var requestManager: RequestManager
    private lateinit var binding: FragmentSimilarShowDetailsBinding
    private val viewModel: SimilarShowsViewModel by viewModels { viewModelFactory }
    private val args: SimilarShowDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_similar_show_details, container, false
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val show = args.tvShow
        Timber.e("The show is ------------ ${show?.name}")
    }

}
