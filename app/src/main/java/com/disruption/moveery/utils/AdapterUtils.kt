package com.disruption.moveery.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.R
import com.disruption.moveery.models.altmovie.AltMovie

/** DiffUtil is a utility class that calculates the difference between two lists and outputs a
 * list of update operations that converts the first list into the second one.*/
class AltMovieDiffCallback : DiffUtil.ItemCallback<AltMovie>() {
    override fun areItemsTheSame(oldItem: AltMovie, newItem: AltMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AltMovie, newItem: AltMovie): Boolean {
        return oldItem == newItem
    }
}

/**The click listener for the RV passing the clicked [AltMovie] in a lambda*/
class AltMovieClickListener(val clickListener: (movie: AltMovie) -> Unit) {
    /**Called when a movie is clicked*/
    fun onClick(movie: AltMovie) = clickListener(movie)
}
