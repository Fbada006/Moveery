package com.disruption.moveery.ui.favourites.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentFavouriteMoviesBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.ui.landing.movies.MoviePageAdapter
import com.disruption.moveery.utils.OnMovieClickListener
import com.disruption.moveery.utils.listenToUserScrolls
import javax.inject.Inject

/**
 * A simple [Fragment] subclass to show fav movies.
 */
class FavouriteMoviesFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentFavouriteMoviesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FavMoviesViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_movies, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MoviePageAdapter(
            requireContext(),
            OnMovieClickListener {
                viewModel.displayMovieDetails(it!!)
            })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.favMoviesList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())
        }

        //The list of movies to display
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) Toast.makeText(context, "Empty here", Toast.LENGTH_SHORT).show()
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { movie ->
                findNavController().navigate(
                    FavouriteMoviesFragmentDirections.actionDestFavouriteMoviesFragmentToDestMovieDetailsFragment(
                        movie
                    )
                )
            }
        })

        // binding.landingMoviesViewModel = viewModel

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.favMoviesList)
    }
}
