package com.disruption.moveery.ui.search.movies

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.disruption.moveery.databinding.FragmentMovieSearchBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.utils.OnMovieClickListener
import com.disruption.moveery.utils.loadImagesWhenScrollIsPaused
import com.disruption.moveery.utils.showAndHandleBackButton
import javax.inject.Inject

/**
 * A simple [Fragment] subclass to handle searching [Movie] objects.
 */
class MovieSearchFragment : Fragment(), Injectable, SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MovieSearchViewModel> { viewModelFactory }
    private lateinit var binding: FragmentMovieSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            SearchedMoviePageAdapter(
                requireContext(),
                OnMovieClickListener {
                    viewModel.displayMovieDetails(it!!)
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
            if (it.isEmpty()) binding.emptyContainer.emptyView.visibility =
                View.VISIBLE else View.GONE
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { movie ->
                findNavController().navigate(
                    MovieSearchFragmentDirections.actionDestSearchFragmentToDestDetailsFragment(
                        movie
                    )
                )
            }
        })

        binding.searchView.setOnQueryTextListener(this)

        binding.toolbar.showAndHandleBackButton(requireActivity())

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.moviesList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()) {
            viewModel.searchMovie(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (TextUtils.isEmpty(newText)) binding.typing.visibility =
            View.VISIBLE else binding.typing.visibility = View.GONE
        return false
    }
}
