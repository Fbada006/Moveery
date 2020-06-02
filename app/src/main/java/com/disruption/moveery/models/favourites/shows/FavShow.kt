package com.disruption.moveery.models.favourites.shows

import androidx.room.Entity
import androidx.room.PrimaryKey

/**The class denoting a favourite show that will be saved to the DB*/
@Entity(tableName = "favShows")
data class FavShow(
    @PrimaryKey
    val id: Int,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    val name: String?,
    val original_language: String?,
    val overview: String?,
    val poster_path: String?,
    val vote_average: Double?
)