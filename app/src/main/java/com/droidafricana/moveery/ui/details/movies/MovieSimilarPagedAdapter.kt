package com.droidafricana.moveery.ui.details.movies

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
import com.droidafricana.moveery.utils.Constants.IMAGE_BASE_URL
import com.droidafricana.moveery.utils.DetailsHelper
import com.droidafricana.moveery.utils.MovieDiffCallback
import com.droidafricana.moveery.utils.OnMovieClickListener
import com.droidafricana.moveery.utils.toPercentage
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

            val rating = item.vote_average?.times(10)?.toInt()

            itemView.tv_movie_language.text = item.original_language
            itemView.tv_movie_rating.apply {
                text = item.vote_average?.toPercentage()
                setTextColor(DetailsHelper.getRatingColor((rating!!), context))
            }

            val posterUrl = IMAGE_BASE_URL + item.poster_path
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