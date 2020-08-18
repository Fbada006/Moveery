package com.droidafricana.moveery.ui.details.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.droidafricana.moveery.R
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.DetailsHelper.getRatingColor
import com.droidafricana.moveery.utils.TvShowDiffCallback
import com.droidafricana.moveery.utils.toPercentage
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

            val rating = item?.vote_average?.times(10)?.toInt()
            itemView.tv_show_language.text = item?.original_language
            itemView.tv_show_rating.apply {
                text = item?.vote_average?.toPercentage()
                setTextColor(getRatingColor((rating!!), context))
            }

            val posterUrl = Constants.IMAGE_BASE_URL + item?.poster_path
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
