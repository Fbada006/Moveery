package com.disruption.moveery.ui.details.shows

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
import kotlinx.android.synthetic.main.show_item_similar.view.*

class ShowSimilarPagedAdapter(private val context: Context) :
    PagedListAdapter<TvShow, ShowSimilarPagedAdapter.TvShowViewHolder>(TvShowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item)
    }

    class TvShowViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context, item: TvShow?) {
            itemView.tv_show_title.text = item?.name
            itemView.tv_show_overview.text = item?.overview
            itemView.tv_show_year.text =
                try {
                    item?.first_air_date?.substring(0, 4)
                } catch (ex: Exception) {
                    "N/A"
                }

            itemView.tv_show_language.text = item?.original_language
            itemView.tv_show_rating.text =
                ((item?.vote_average)!! * 10).toInt().toString().plus("%")

            val posterUrl = Constants.IMAGE_BASE_URL + item.poster_path
            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(itemView.iv_show_poster)
        }

        //For inflating the layout in onCreateViewHolder()
        companion object {
            fun from(parent: ViewGroup): TvShowViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.show_item_similar, parent, false)
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