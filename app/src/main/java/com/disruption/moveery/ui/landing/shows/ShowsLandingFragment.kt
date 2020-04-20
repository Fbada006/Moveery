package com.disruption.moveery.ui.landing.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.disruption.moveery.R

class ShowsLandingFragment : Fragment() {

    companion object {
        fun newInstance() = ShowsLandingFragment()
    }

    private lateinit var viewModel: ShowsLandingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shows_landing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowsLandingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
