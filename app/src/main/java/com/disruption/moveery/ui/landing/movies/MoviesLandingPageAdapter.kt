package com.disruption.moveery.ui.landing.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.R
import com.disruption.moveery.models.movies.movie.Movie
import com.disruption.moveery.utils.Constants
import kotlinx.android.synthetic.main.movie_item.view.*

/**Adapter to handle displaying [Movie] objects in the [MoviesLandingFragment]*/
class LandingPageAdapter(
    private val context: Context,
    private val onClickListener: OnMovieClickListener
) :
    PagedListAdapter<Movie, LandingPageAdapter.MovieViewHolder>(
        MovieDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(context, movie)
    }

    /**The [RecyclerView.ViewHolder] for the [Movie] objects*/
    class MovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**Binds data to the [MovieViewHolder]*/
        fun bind(context: Context, item: Movie?) {
            itemView.tv_movie_title.text = item?.title

            val posterUrl = Constants.IMAGE_BASE_URL + item?.poster_path
            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(itemView.iv_movie_poster)
        }

        companion object {
            /**For inflating the layout in [onCreateViewHolder]*/
            fun from(parent: ViewGroup): MovieViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
                return MovieViewHolder(
                    view
                )
            }
        }
    }
}

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