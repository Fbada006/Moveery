package com.disruption.moveery.ui.landing.shows

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
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.utils.Constants
import kotlinx.android.synthetic.main.tv_show_item.view.*

class ShowsLandingPageAdapter(private val context: Context) :
    PagedListAdapter<TvShow, ShowsLandingPageAdapter.TvShowViewHolder>(TvShowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(context, getItem(position))
    }

    class TvShowViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**Binds data to the [TvShowViewHolder]*/
        fun bind(context: Context, item: TvShow?) {
            itemView.tv_show_title.text = item?.name
            val posterUrl = Constants.IMAGE_BASE_URL + item?.poster_path

            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(itemView.iv_show_poster)
        }

        companion object {
            fun from(parent: ViewGroup): TvShowViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tv_show_item, parent, false)
                return TvShowViewHolder(view)
            }
        }
    }
}

class TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }
}