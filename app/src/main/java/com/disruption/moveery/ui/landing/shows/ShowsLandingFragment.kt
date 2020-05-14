package com.disruption.moveery.ui.landing.shows

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
import com.disruption.moveery.databinding.ShowsLandingFragmentBinding
import com.disruption.moveery.di.Injectable
import com.disruption.moveery.ui.settings.SettingsActivity
import com.disruption.moveery.utils.OnShowClickListener
import com.disruption.moveery.utils.listenToUserScrolls
import timber.log.Timber
import javax.inject.Inject

/**Displays the shows on the Discover Shows screen*/
class ShowsLandingFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ShowsLandingFragment()
    }

    private val viewModel by viewModels<ShowsLandingViewModel> { viewModelFactory }
    private lateinit var binding: ShowsLandingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShowsLandingFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShowsLandingPageAdapter(requireContext(),
            OnShowClickListener { it?.let { tvShow -> viewModel.displayShowDetails(tvShow) } })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.showsList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())
        }

        viewModel.showsList.observe(viewLifecycleOwner, Observer {
            Timber.e("Shows list -----------------: $it")
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedShow.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { tvShow ->
                findNavController().navigate(
                    ShowsLandingFragmentDirections.actionDestShowsLandingFragmentToDestShowDetailsFragment(
                        tvShow
                    )
                )
            }
        })

        binding.landingShowsViewModel = viewModel

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.showsList)
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
                findNavController().navigate(ShowsLandingFragmentDirections.actionDestShowsLandingFragmentToDestShowsSearchFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
