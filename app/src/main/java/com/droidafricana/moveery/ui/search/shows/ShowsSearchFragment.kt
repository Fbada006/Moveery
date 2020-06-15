package com.droidafricana.moveery.ui.search.shows

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
import com.droidafricana.moveery.databinding.ShowsSearchFragmentBinding
import com.droidafricana.moveery.di.Injectable
import com.droidafricana.moveery.utils.OnShowClickListener
import com.droidafricana.moveery.utils.loadImagesWhenScrollIsPaused
import com.droidafricana.moveery.utils.showAndHandleBackButton
import javax.inject.Inject

class ShowsSearchFragment : Fragment(), Injectable, SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = ShowsSearchFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ShowsSearchViewModel> { viewModelFactory }
    private lateinit var binding: ShowsSearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShowsSearchFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            SearchedShowPageAdapter(requireContext(),
                OnShowClickListener { it?.let { tvShow -> viewModel.displayShowDetails(tvShow) } })

        val carouselManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL)
        carouselManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.showsList.apply {
            this.adapter = adapter
            layoutManager = carouselManager
            addOnScrollListener(CenterScrollListener())
        }

        //The list of movies to display
        viewModel.showList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) binding.emptyContainer.emptyView.visibility =
                View.VISIBLE else View.GONE
            adapter.submitList(it)
        })

        //Observe the navigation event as well using the convenient Event class
        viewModel.navigateToSelectedShow.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { tvShow ->
                findNavController().navigate(
                    ShowsSearchFragmentDirections.actionDestShowsSearchFragmentToDestShowDetailsFragment(
                        tvShow
                    )
                )
            }
        })

        binding.searchView.setOnQueryTextListener(this)

        binding.toolbar.showAndHandleBackButton(requireActivity())

        //Listen to the scrolls appropriately for efficient loading with user data in mind
        loadImagesWhenScrollIsPaused(binding.showsList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()) {
            viewModel.searchShow(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (TextUtils.isEmpty(newText)) binding.typing.visibility =
            View.VISIBLE else binding.typing.visibility = View.GONE
        return false
    }
}
