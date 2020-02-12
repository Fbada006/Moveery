package com.disruption.moveery.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.disruption.moveery.databinding.FragmentLandingBinding
import com.disruption.moveery.db.MovieRoomDatabase
import com.disruption.moveery.repo.MovieRepo

class LandingFragment : Fragment() {
    val TAG = "LandingFragment"
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

        val adapter = LandingPageAdapter(context!!)
        binding.moviesList.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}
