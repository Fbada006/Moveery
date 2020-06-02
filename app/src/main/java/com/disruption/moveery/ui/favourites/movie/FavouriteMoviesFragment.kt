package com.disruption.moveery.ui.favourites.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.disruption.moveery.R
import com.disruption.moveery.databinding.FragmentFavouriteMoviesBinding

/**
 * A simple [Fragment] subclass to show fav movies.
 */
class FavouriteMoviesFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_movies, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }
}
