package com.disruption.moveery.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**This is the model that is queried from the network and saved in the db
 * TODO: Update domain and data models
 * */
@Entity(tableName = "movies")
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Int,
    @Json(name = "adult")
    val isForAdult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    @Json(name = "video")
    val hasVideo: Boolean?,
    val vote_average: Double?
) : Parcelable