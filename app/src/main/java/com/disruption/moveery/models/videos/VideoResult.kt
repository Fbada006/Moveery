package com.disruption.moveery.models.videos

/**The result of [Video] that works for either shows and movies*/
data class VideoResult(
    val id: Int,
    val results: List<Video>
)