package com.droidafricana.moveery.ui.landing.shows

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
import com.droidafricana.moveery.utils.OnShowClickListener
import com.droidafricana.moveery.utils.TvShowDiffCallback
import kotlinx.android.synthetic.main.tv_show_item.view.*

/**Handle displaying a paged list of [TvShow] objects on the [ShowsLandingFragment]
 * using paging for efficient loading from the API*/
class ShowsPageAdapter(
    private val context: Context,
    private val onClickListener: OnShowClickListener
) :
    PagedListAdapter<TvShow, ShowsPageAdapter.TvShowViewHolder>(TvShowDiffCallback()) {

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
