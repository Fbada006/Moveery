package com.droidafricana.moveery.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.droidafricana.moveery.R
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.utils.Constants.YOUTUBE_IMAGE_BASE_URL
import com.droidafricana.moveery.utils.OnVideoClickListener
import com.droidafricana.moveery.utils.VideoDiffCallback
import kotlinx.android.synthetic.main.video_item.view.*

/**For displaying videos*/
class VideoAdapter(
    private val context: Context,
    private val onClickListener: OnVideoClickListener
) : ListAdapter<Video, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        holder.bind(video, context)
        holder.itemView.iv_video_play.setOnClickListener {
            onClickListener.onClick(video)
        }
    }

    class VideoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Video, context: Context) {
            val youtubeThumbnail = String.format(YOUTUBE_IMAGE_BASE_URL, item.key)
            Glide.with(context)
                .load(youtubeThumbnail)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_loading_animation)
                .into(itemView.iv_video_poster)
        }

        //For inflating the layout in onCreateViewHolder()
        companion object {
            fun from(parent: ViewGroup): VideoViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
                return VideoViewHolder(view)
            }
        }
    }
}
