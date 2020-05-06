package com.disruption.moveery.ui.details.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R
import com.disruption.moveery.databinding.ShowDetailsFragmentBinding
import javax.inject.Inject

/**Fragment to show details of a clicked [TvShow]*/
class ShowDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var requestManager: RequestManager

    companion object {
        fun newInstance() = ShowDetailsFragment()
    }

    private val viewModel by viewModels<ShowDetailsViewModel> { viewModelFactory }
    private lateinit var binding: ShowDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.show_details_fragment, container, false)
        return binding.root
    }
}