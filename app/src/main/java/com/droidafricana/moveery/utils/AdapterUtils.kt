package com.droidafricana.moveery.utils

import androidx.recyclerview.widget.DiffUtil
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.videos.Video

/** DiffUtil is a utility class that calculates the difference between two lists and outputs a
 * list of update operations that converts the first list into the second one.*/
class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

/**The click listener for the RV passing the clicked [Movie] in a lambda*/
class OnMovieClickListener(val clickListener: (movie: Movie?) -> Unit) {
    /**Called when a movie is clicked*/
    fun onClick(movie: Movie?) = clickListener(movie)
}

/** DiffUtil is a utility class that calculates the difference between two lists and outputs a
 * list of update operations that converts the first list into the second one.*/
class TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }
}

/**The click listener for the RV passing the clicked [TvShow] in a lambda*/
class OnShowClickListener(val clickListener: (show: TvShow?) -> Unit) {
    /**Called when a show is clicked*/
    fun onClick(show: TvShow?) = clickListener(show)
}

/** DiffUtil is a utility class that calculates the difference between two lists and outputs a
 * list of update operations that converts the first list into the second one.*/
class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}

/**The click listener for the RV passing the clicked [Video] in a lambda*/
class OnVideoClickListener(val clickListener: (video: Video?) -> Unit) {
    /**Called when a video is clicked*/
    fun onClick(video: Video?) = clickListener(video)
}
