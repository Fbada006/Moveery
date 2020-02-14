package com.disruption.moveery.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.disruption.moveery.databinding.FragmentDetailsBinding

/**
 * A fragment to show the details of the movie
 */
class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }
}
