package com.droidafricana.moveery.models.videos

/**The video for a show or movie*/
data class Video(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)