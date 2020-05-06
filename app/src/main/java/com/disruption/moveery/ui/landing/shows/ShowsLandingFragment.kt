package com.disruption.moveery.ui.landing.shows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.disruption.moveery.R
import com.disruption.moveery.di.Injectable
import javax.inject.Inject

class ShowsLandingFragment : Fragment(), Injectable {

    val TAG = "ShowsLandingFragment "

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

        viewModel.showsList.observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "Shows list -----------------: $it")
        })
        return inflater.inflate(R.layout.shows_landing_fragment, container, false)
    }
}
