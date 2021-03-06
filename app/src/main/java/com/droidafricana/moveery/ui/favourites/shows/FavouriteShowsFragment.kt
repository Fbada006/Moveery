package com.droidafricana.moveery.ui.favourites.shows

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
import com.droidafricana.moveery.R
import com.droidafricana.moveery.databinding.FragmentFavouriteShowsBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.ui.landing.shows.ShowsPageAdapter
import com.droidafricana.moveery.ui.settings.SettingsActivity
import com.droidafricana.moveery.utils.OnShowClickListener
import com.droidafricana.moveery.utils.loadImagesWhenScrollIsPaused
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
        setHasOptionsMenu(true)
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
        viewModel.navigateToSelectedShow.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { tvShow ->
                findNavController().navigate(
                    FavouriteShowsFragmentDirections.actionDestFavouriteShowsFragmentToDestShowDetailsFragment(
                        tvShow
                    )
                )
            }
        })

        binding.nukeFavourites.setOnClickListener { showNukeConfirmation() }

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.favShowsList)
    }

    private fun showNukeConfirmation() {
        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.confirm_nuke))
            .setContentText(getString(R.string.sure_to_delete))
            .setConfirmText(getString(android.R.string.ok))
            .setConfirmClickListener {
                viewModel.nukeShowFavourites()
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
                    FavouriteShowsFragmentDirections.actionDestFavouriteShowsFragmentToDestShowsSearchFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
