package com.disruption.moveery.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentSearchBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.utils.AltMovieClickListener
import com.disruption.moveery.utils.listenToUserScrolls
import javax.inject.Inject

/**
 * A simple [Fragment] subclass to handle searching [Movie] objects.
 */
class SearchFragment : Fragment(), Injectable, SearchView.OnQueryTextListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)

        val adapter = SearchedMoviePageAdapter(requireContext(), AltMovieClickListener {
            viewModel.displayMovieDetails(it)
        })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.moviesList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())
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
        val toolbar = binding.toolbar
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()) {
            viewModel.searchMovie(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //Do nothing for now
        //TODO: Show some animation
        return false
    }
}
