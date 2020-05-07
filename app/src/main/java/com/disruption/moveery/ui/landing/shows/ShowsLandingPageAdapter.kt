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

/**Handle displaying a paged list of [TvShow] objects on the [ShowsLandingFragment]
 * using paging for efficient loading from the API*/
class ShowsLandingPageAdapter(
    private val context: Context,
    private val onClickListener: OnShowClickListener
) :
    PagedListAdapter<TvShow, ShowsLandingPageAdapter.TvShowViewHolder>(TvShowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.bind(context, tvShow)
        holder.itemView.setOnClickListener { onClickListener.onClick(tvShow) }
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
            /**For inflating the item layout*/
            fun from(parent: ViewGroup): TvShowViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tv_show_item, parent, false)
                return TvShowViewHolder(view)
            }
        }
    }
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