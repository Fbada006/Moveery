package com.droidafricana.moveery.models.shows

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**The Pojo denoting a TvShow and doubles up as the Entity for Room*/
@Entity(tableName = "shows")
@Parcelize
data class TvShow(
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    @PrimaryKey
    val id: Int,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
) : Parcelable