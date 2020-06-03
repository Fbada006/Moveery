package com.disruption.moveery.ui.favourites.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.disruption.moveery.databinding.FragmentFavouriteShowsBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.ui.landing.shows.ShowsPageAdapter
import com.disruption.moveery.utils.OnShowClickListener
import com.disruption.moveery.utils.listenToUserScrolls
import javax.inject.Inject

/**
 * A simple [Fragment] subclass to show favourite shows.
 */
class FavouriteShowsFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentFavouriteShowsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FavShowsViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_shows, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ShowsPageAdapter(requireContext(),
            OnShowClickListener { it?.let { tvShow -> viewModel.displayShowDetails(tvShow) } })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.favShowsList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())
        }

        viewModel.showsList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) binding.emptyContainer.emptyView.visibility =
                View.VISIBLE else View.GONE
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedShow.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { tvShow ->
                findNavController().navigate(
                    FavouriteShowsFragmentDirections.actionDestFavouriteShowsFragmentToDestShowDetailsFragment(
                        tvShow
                    )
                )
            }
        })

        //binding.landingShowsViewModel = viewModel

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.favShowsList)
    }
}
