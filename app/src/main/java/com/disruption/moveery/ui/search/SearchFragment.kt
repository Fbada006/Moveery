package com.disruption.moveery.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.disruption.moveery.MainActivity
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentSearchBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.utils.FragmentHelper.listenToUserScrolls

/**
 * A simple [Fragment] subclass to handle searching [Movie] objects.
 */
class SearchFragment : Fragment(), Injectable, SearchView.OnQueryTextListener {
    val TAG = "SearchFragment"
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)

        val adapter = SearchPageAdapter(requireContext(), OnSearchedMovieClickListener {
            viewModel.displayMovieDetails(it)
        })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.moviesList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
        }

        //The list of movies to display
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { movie ->
                findNavController().navigate(
                    SearchFragmentDirections.actionDestSearchFragmentToDestDetailsFragment(
                        null,
                        movie
                    )
                )
            }
        })

        binding.searchView.setOnQueryTextListener(this)

        showAndHandleBackButton()

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.moviesList)

        return binding.root
    }

    private fun showAndHandleBackButton() {
        val toolbar = binding.toolbarSearch
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity?)!!.supportActionBar!!.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()) {
            viewModel.searchMovie(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //Do nothing for now
        return false
    }
}
