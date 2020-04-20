package com.disruption.moveery.ui.search.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.disruption.moveery.R

class ShowsSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ShowsSearchFragment()
    }

    private lateinit var viewModel: ShowsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shows_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowsSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
