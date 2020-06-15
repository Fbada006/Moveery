package com.droidafricana.moveery.ui.landing.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentLandingMoviesBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.ui.settings.SettingsActivity
import com.droidafricana.moveery.utils.OnMovieClickListener
import com.droidafricana.moveery.utils.loadImagesWhenScrollIsPaused
import javax.inject.Inject

/**The fragment that is first launched when the user opens the app*/
class MoviesLandingFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MoviesLandingViewModel> { viewModelFactory }
    private lateinit var binding: FragmentLandingMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingMoviesBinding.inflate(inflater)
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.landingMoviesViewModel = viewModel

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
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
                    MoviesLandingFragmentDirections.actionDestMoviesLandingFragmentToDetailsFragment(
                        movie
                    )
                )
            }
        })

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.moviesList)
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
                findNavController().navigate(MoviesLandingFragmentDirections.actionDestMoviesLandingFragmentToDestSearchFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

