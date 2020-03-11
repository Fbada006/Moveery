package com.disruption.moveery.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.R
import com.disruption.moveery.models.SearchedMovie
import com.disruption.moveery.utils.Constants

/**Adapter to handle displaying [SearchedMovie] objects in the [SearchFragment]*/
class SearchPageAdapter(
    private val context: Context,
    private val onClickListener: OnSearchedMovieClickListener
) :
    PagedListAdapter<SearchedMovie, SearchPageAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(context, movie)
    }

    /**The [RecyclerView.ViewHolder] for the [SearchedMovie] objects*/
    class MovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieTitle =
            itemView.findViewById<TextView>(R.id.tv_movie_title)
        private val movieOverview =
            itemView.findViewById<TextView>(R.id.tv_movie_overview)
        private val movieYear =
            itemView.findViewById<TextView>(R.id.tv_movie_year)
        private val movieLang =
            itemView.findViewById<TextView>(R.id.tv_movie_language)
        private val movieImage =
            itemView.findViewById<AppCompatImageView>(R.id.iv_movie_poster)

        /**Binds data to the [MovieViewHolder]*/
        fun bind(context: Context, item: SearchedMovie) {
            movieTitle.text = item.title
            movieOverview.text = item.overview
            movieYear.text =
                try {
                    item.release_date?.substring(0, 4)
                } catch (ex: Exception) {
                    "N/A"
                }

            movieLang.text = item.original_language

            val posterUrl = Constants.IMAGE_BASE_URL + item.poster_path
            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(movieImage)
        }

        companion object {
            /**For inflating the layout in [onCreateViewHolder]*/
            fun from(parent: ViewGroup): MovieViewHolder {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_search_item, parent, false)
                return MovieViewHolder(view)
            }
        }
    }
}

/** DiffUtil is a utility class that calculates the difference between two lists and outputs a
 * list of update operations that converts the first list into the second one.*/
class MovieDiffCallback : DiffUtil.ItemCallback<SearchedMovie>() {
    override fun areItemsTheSame(oldItem: SearchedMovie, newItem: SearchedMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchedMovie, newItem: SearchedMovie): Boolean {
        return oldItem == newItem
    }
}

/**The click listener for the RV passing the clicked [SearchedMovie] in a lambda*/
class OnSearchedMovieClickListener(val clickListener: (movie: SearchedMovie) -> Unit) {
    /**Called when a movie is clicked*/
    fun onClick(movie: SearchedMovie) = clickListener(movie)
}