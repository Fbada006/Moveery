package com.disruption.moveery.models.altmovie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**This is the alternative POJO using GSON and handles searched movies as well as
 * similar movies*/
@Parcelize
data class AltMovie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
): Parcelable