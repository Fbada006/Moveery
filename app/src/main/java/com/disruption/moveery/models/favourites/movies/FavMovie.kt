package com.disruption.moveery.models.favourites.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

/**The class denoting a favourite movie that will be saved to the DB*/
@Entity(tableName = "favMovies")
data class FavMovie(
    @PrimaryKey
    val id: Int,
    val isForAdult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val hasVideo: Boolean?,
    val vote_average: Double?
)