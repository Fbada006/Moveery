package com.disruption.moveery.ui.landing.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.disruption.moveery.databinding.ShowDetailsFragmentBinding
import com.disruption.moveery.di.Injectable
import timber.log.Timber
import javax.inject.Inject

class ShowsLandingFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ShowsLandingFragment()
    }

    private val viewModel by viewModels<ShowsLandingViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ShowDetailsFragmentBinding.inflate(inflater)

        viewModel.showsList.observe(viewLifecycleOwner, Observer {
            Timber.e("Shows list -----------------: $it")
        })

        return binding.root
    }
}
