package com.disruption.moveery.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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

/**Function to make the status bar transparent*/
fun Fragment.makeStatusBarTransparent() {
    this.requireActivity().window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}
