package com.disruption.moveery.utils

import android.widget.ImageView
import com.bumptech.glide.RequestManager

/**Extension function on [ImageView] to load images with [Glide]*/
fun ImageView.loadImage(url: String, requestManager: RequestManager) {
    requestManager
        .load(url)
        .into(this)
}