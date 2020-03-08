package com.disruption.moveery.ui.landing

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
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentLandingBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.ui.settings.SettingsActivity
import com.disruption.moveery.utils.LandingHelper.listenToUserScrolls
import javax.inject.Inject


/**The fragment that is first launched when the user opens the app*/
class LandingFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LandingViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLandingBinding.inflate(inflater)

        setHasOptionsMenu(true)

        val adapter = LandingPageAdapter(requireContext(), OnMovieClickListener {
            viewModel.displayMovieDetails(it)
        })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.moviesList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())

        }

        //LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        //The list of movies to display
        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { movie ->
                findNavController().navigate(
                    LandingFragmentDirections.actionDestLandingFragmentToDetailsFragment(movie)
                )
            }
        })

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.moviesList)

        // Inflate the layout for this fragment
        return binding.root
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}

