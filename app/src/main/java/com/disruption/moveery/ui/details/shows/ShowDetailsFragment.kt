package com.disruption.moveery.ui.details.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.disruption.moveery.R

class ShowDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ShowDetailsFragment()
    }

    private lateinit var viewModel: ShowDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
