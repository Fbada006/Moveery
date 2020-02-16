package com.disruption.moveery.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.disruption.moveery.databinding.FragmentLandingBinding
import com.disruption.moveery.db.MovieRoomDatabase
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.DetailsHelper

/**The fragment that is first launched when the user opens the app*/
class LandingFragment : Fragment() {

    /**
     * Lazily initialize our [LandingViewModel].
     */
    private val viewModel by viewModels<LandingViewModel> {
        LandingViewModel.FACTORY(MovieRepo(MovieRoomDatabase.getDatabase(requireContext())))
    }

    /**Called when the fragment is created*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLandingBinding.inflate(inflater)

        val adapter = LandingPageAdapter(context!!, OnMovieClickListener {
            viewModel.displayMovieDetails(it)
        })

        binding.moviesList.adapter = adapter
        binding.moviesList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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
        DetailsHelper.listenToUserScrolls(requireContext(), binding.moviesList)

        // Inflate the layout for this fragment
        return binding.root
    }
}
