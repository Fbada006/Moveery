package com.disruption.moveery.ui.search.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.disruption.moveery.R
import com.disruption.moveery.models.movies.altmovie.AltMovie
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.utils.Constants.IMAGE_BASE_URL
import com.disruption.moveery.utils.OnShowClickListener
import com.disruption.moveery.utils.TvShowDiffCallback
import kotlinx.android.synthetic.main.show_search_item.view.*

/**Adapter to handle displaying [AltMovie] objects in the [ShowsSearchFragment]*/
class SearchedShowPageAdapter(
    private val context: Context,
    private val onClickListener: OnShowClickListener
) : PagedListAdapter<TvShow, SearchedShowPageAdapter.SearchedShowViewHolder>(
    TvShowDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedShowViewHolder {
        return SearchedShowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchedShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.bind(context, tvShow)
        holder.itemView.setOnClickListener { onClickListener.onClick(tvShow) }
    }

    /**The [RecyclerView.ViewHolder] for the [TvShow] objects*/
    class SearchedShowViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**Binds data to the [SearchedShowViewHolder]*/
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

            val posterUrl = IMAGE_BASE_URL + item.poster_path
            Glide.with(context)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(itemView.iv_show_poster)
        }

        companion object {
            /**For inflating the layout in [onCreateViewHolder]*/
            fun from(parent: ViewGroup): SearchedShowViewHolder {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.show_search_item, parent, false)
                return SearchedShowViewHolder(
                    view
                )
            }
        }
    }
}

