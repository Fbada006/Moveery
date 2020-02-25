package com.disruption.moveery.utils

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**Helper methods used in the LandingFragment*/
object LandingHelper {

    /**This method listens to scrolls and determines if Glide should load images*/
    fun Fragment.listenToUserScrolls(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ||
                    newState == RecyclerView.SCROLL_STATE_SETTLING
                ) {
                    //Load the images because user is no longer scrolling
                    Glide.with(requireContext()).resumeRequests()
                } else {
                    //No point loading the images if the user scrolling
                    Glide.with(requireContext()).pauseRequests()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}