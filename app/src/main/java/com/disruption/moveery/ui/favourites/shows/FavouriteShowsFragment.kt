package com.disruption.moveery.ui.favourites.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentFavouriteShowsBinding

/**
 * A simple [Fragment] subclass to show favourite shows.
 */
class FavouriteShowsFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_shows, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}
