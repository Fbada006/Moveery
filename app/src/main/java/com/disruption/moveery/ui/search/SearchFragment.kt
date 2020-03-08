package com.disruption.moveery.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.disruption.moveery.databinding.FragmentSearchBinding

/**
 * A simple [Fragment] subclass to handle searching [Movie] objects.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSearchBinding.inflate(inflater)

        return binding.root
    }
}
