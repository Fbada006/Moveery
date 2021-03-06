package com.droidafricana.moveery.ui.landing.shows

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
import com.droidafricana.moveery.databinding.ShowsLandingFragmentBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.ui.settings.SettingsActivity
import com.droidafricana.moveery.utils.OnShowClickListener
import com.droidafricana.moveery.utils.loadImagesWhenScrollIsPaused
import com.droidafricana.moveery.utils.showCloseSnack
import javax.inject.Inject

/**Displays the shows on the Discover Shows screen*/
class ShowsLandingFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

        val adapter = ShowsPageAdapter(
            requireContext(),
            OnShowClickListener { it?.let { tvShow -> viewModel.displayShowDetails(tvShow) } })

        binding.lifecycleOwner = viewLifecycleOwner

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.showsList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())
        }

        viewModel.showsList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer { showCloseSnack(it) })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) binding.loadingSpinner.visibility =
                View.VISIBLE else binding.loadingSpinner.visibility = View.GONE
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

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.showsList)
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
