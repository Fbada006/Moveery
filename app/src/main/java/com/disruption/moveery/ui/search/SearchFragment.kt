package com.disruption.moveery.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.disruption.moveery.databinding.FragmentSearchBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.utils.LandingHelper.listenToUserScrolls

/**
 * A simple [Fragment] subclass to handle searching [Movie] objects.
 */
class SearchFragment : Fragment(), Injectable {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSearchBinding.inflate(inflater)

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

        viewModel.searchMovie("bond")

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.moviesList)

        return binding.root
    }
}
