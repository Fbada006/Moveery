package com.disruption.moveery.ui.landing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.disruption.moveery.R
import com.disruption.moveery.models.Movie
import com.disruption.moveery.utils.Constants

class LandingPageAdapter(
    private val context: Context,
    private val onClickListener: OnMovieClickListener
) :
    ListAdapter<Movie, LandingPageAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(context, movie)
    }

    class MovieViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieTitle =
            itemView.findViewById<TextView>(R.id.tv_movie_title)
        private val movieImage =
            itemView.findViewById<AppCompatImageView>(R.id.iv_movie_poster)

        fun bind(context: Context, item: Movie) {
            //TODO: Bind your views here
            movieTitle.text = item.title
            val posterUrl = Constants.IMAGE_BASE_URL + item.poster_path

            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .placeholder(R.drawable.movie_loading_animation)
                .into(movieImage)
        }

        //For inflating the layout in onCreateViewHolder()
        //TODO: Make sure your layout name is resolved
        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
                return MovieViewHolder(view)
            }
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        //TODO: Confirm that your id variable matches this one or change this one to match
        //the one in your model
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

class OnMovieClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}