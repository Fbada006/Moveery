package com.disruption.moveery.ui.details.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.R
import com.disruption.moveery.models.movies.movie.Movie
import com.disruption.moveery.utils.Constants
import com.disruption.moveery.utils.MovieDiffCallback
import com.disruption.moveery.utils.OnMovieClickListener
import kotlinx.android.synthetic.main.movie_similar_item.view.*

/**For displaying similar movies in the [MovieDetailsFragment]*/
class MovieSimilarPagedAdapter(
    private val context: Context,
    private val onClickListener: OnMovieClickListener
) :
    PagedListAdapter<Movie, MovieSimilarPagedAdapter.SimilarMovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        return SimilarMovieViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(context, movie)
    }

    class SimilarMovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**Binds data to the [SimilarMovieViewHolder]*/
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

        //For inflating the layout in onCreateViewHolder()
        companion object {
            fun from(parent: ViewGroup): SimilarMovieViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_similar_item, parent, false)
                return SimilarMovieViewHolder(
                    view
                )
            }
        }
    }
}