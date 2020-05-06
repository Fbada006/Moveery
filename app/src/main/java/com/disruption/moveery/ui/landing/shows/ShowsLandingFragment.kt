package com.disruption.moveery.ui.landing.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.disruption.moveery.databinding.ShowsLandingFragmentBinding
import com.disruption.moveery.di.Injectable
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShowsLandingPageAdapter(requireContext())

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

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        listenToUserScrolls(binding.showsList)
    }
}
