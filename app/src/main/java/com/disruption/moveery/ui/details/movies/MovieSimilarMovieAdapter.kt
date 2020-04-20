package com.disruption.moveery.ui.details.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.R
import com.disruption.moveery.models.movies.altmovie.AltMovie
import com.disruption.moveery.utils.AltMovieClickListener
import com.disruption.moveery.utils.AltMovieDiffCallback
import com.disruption.moveery.utils.Constants

/**For displaying similar movies in the [MovieDetailsFragment]*/
class MovieSimilarMovieAdapter(
    private val context: Context,
    private val onClickListener: AltMovieClickListener
) :
    PagedListAdapter<AltMovie, MovieSimilarMovieAdapter.SimilarMovieViewHolder>(AltMovieDiffCallback()) {

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
        private val movieTitle =
            itemView.findViewById<TextView>(R.id.tv_movie_title)
        private val movieOverview =
            itemView.findViewById<TextView>(R.id.tv_movie_overview)
        private val movieYear =
            itemView.findViewById<TextView>(R.id.tv_movie_year)
        private val movieLang =
            itemView.findViewById<TextView>(R.id.tv_movie_language)
        private val movieRating =
            itemView.findViewById<TextView>(R.id.tv_movie_rating)
        private val movieImage =
            itemView.findViewById<AppCompatImageView>(R.id.iv_movie_poster)

        /**Binds data to the [SimilarMovieViewHolder]*/
        fun bind(context: Context, item: AltMovie) {
            movieTitle.text = item.title
            movieOverview.text = item.overview
            movieYear.text =
                try {
                    item.release_date?.substring(0, 4)
                } catch (ex: Exception) {
                    "N/A"
                }

            movieLang.text = item.original_language
            movieRating.text = ((item.vote_average)!! * 10).toInt().toString().plus("%")

            val posterUrl = Constants.IMAGE_BASE_URL + item.poster_path
            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(movieImage)
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