package com.disruption.moveery.utils

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R
import com.disruption.moveery.models.videos.Video
import timber.log.Timber

/**Extension function on [ImageView] to load images with [RequestManager]*/
fun ImageView.loadImage(url: String, requestManager: RequestManager) {
    requestManager
        .load(url)
        .into(this)
}

/**Show and handle the back button for fragments*/
fun Toolbar.showAndHandleBackButton(activity: FragmentActivity?) {
    this.apply {
        setNavigationIcon(R.drawable.ic_arrow_back)
        setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}

/**Convert double to percentage string*/
fun Double.toPercentage(): String {
    return try {
        String.format("%.0f%%%n", this.times(10))
    } catch (ex: Exception) {
        "N/A"
    }
}

/**This method listens to scrolls and determines if Glide should load images*/
fun Fragment.loadImagesWhenScrollIsPaused(recyclerView: RecyclerView) {
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

/**Play a youtube video or show error*/
fun Fragment.playVideo(video: Video?) {
    val videoLink = String.format(Constants.YOUTUBE_VIDEO_BASE_URL, video?.key)
    val videoIntent =
        Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
    try {
        requireContext().startActivity(videoIntent)
    } catch (ex: Exception) {
        Timber.e("Error playing video ----------- $ex")
        Toast.makeText(context, getString(R.string.cannot_play_video), Toast.LENGTH_SHORT)
            .show()
    }
}

/**Show a view*/
fun View.showView() {
    if (this.visibility == View.GONE) this.visibility = View.VISIBLE
}

/**Hide a view*/
fun View.hideView() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}
