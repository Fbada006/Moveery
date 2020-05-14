package com.disruption.moveery.utils

import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.RequestManager
import com.disruption.moveery.R

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
