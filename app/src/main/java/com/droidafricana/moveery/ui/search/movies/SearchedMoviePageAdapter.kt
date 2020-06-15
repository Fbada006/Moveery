package com.droidafricana.moveery.ui.search.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.droidafricana.moveery.R
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.MovieDiffCallback
import com.droidafricana.moveery.utils.OnMovieClickListener
import kotlinx.android.synthetic.main.movie_search_item.view.*

/**Adapter to handle displaying [Movie] objects in the [MovieSearchFragment]*/
class SearchedMoviePageAdapter(
    private val context: Context,
    private val onClickListener: OnMovieClickListener
) : PagedListAdapter<Movie, SearchedMoviePageAdapter.SearchedMovieViewHolder>(
    MovieDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedMovieViewHolder {
        return SearchedMovieViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: SearchedMovieViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(context, movie)
    }

    /**The [RecyclerView.ViewHolder] for the [Movie] objects*/
    class SearchedMovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**Binds data to the [SearchedMovieViewHolder]*/
        fun bind(context: Context, item: Movie) {
            itemView.tv_movie_title.text = item.title
            itemView.tv_movie_overview.text = item.overview
            itemView.tv_movie_year.text =
                try {
                    item.release_date?.substring(0, 4)
                } catch (ex: Exception) {
                    "N/A"
                }

            itemView.tv_movie_language.text = item.original_language
            itemView.tv_movie_rating.text =
                ((item.vote_average)!! * 10).toInt().toString().plus("%")

            val posterUrl = Constants.IMAGE_BASE_URL + item.poster_path
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
            fun from(parent: ViewGroup): SearchedMovieViewHolder {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_search_item, parent, false)
                return SearchedMovieViewHolder(
                    view
                )
            }
        }
    }
}

