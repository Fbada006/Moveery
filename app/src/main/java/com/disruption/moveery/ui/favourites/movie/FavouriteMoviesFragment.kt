package com.disruption.moveery.ui.favourites.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentFavouriteMoviesBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.ui.landing.movies.MoviePageAdapter
import com.disruption.moveery.ui.settings.SettingsActivity
import com.disruption.moveery.utils.OnMovieClickListener
import com.disruption.moveery.utils.loadImagesWhenScrollIsPaused
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
        setHasOptionsMenu(true)
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
            if (it.isEmpty()) {
                binding.emptyContainer.emptyView.visibility = View.VISIBLE
                binding.nukeFavourites.visibility = View.GONE
            } else {
                binding.emptyContainer.emptyView.visibility = View.GONE
                binding.nukeFavourites.visibility = View.VISIBLE
            }
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

        binding.nukeFavourites.setOnClickListener { showNukeConfirmation() }

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.favMoviesList)
    }

    private fun showNukeConfirmation() {
        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.confirm_nuke))
            .setContentText(getString(R.string.sure_to_delete))
            .setConfirmText(getString(android.R.string.ok))
            .setConfirmClickListener {
                viewModel.nukeMovieFavourites()
                it.dismissWithAnimation()
            }
            .setCancelText(getString(android.R.string.no))
            .setCancelClickListener { it.dismissWithAnimation() }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
                true
            }
            R.id.action_search -> {
                findNavController().navigate(
                    FavouriteMoviesFragmentDirections.actionDestFavouriteMoviesFragmentToDestMovieSearchFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
