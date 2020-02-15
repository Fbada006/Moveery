package com.disruption.moveery.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.disruption.moveery.R

/**Handles getting the genres of the selected [Movie]*/
object DetailsHelper {
    /**Returns the genres of the movie from the given [genreIds]*/
    fun getGenres(genreIds: List<Int>, context: Context): String {
        val stringBuilder = StringBuilder()
        genreIds.forEach {
            when (it) {
                28 -> stringBuilder.append(context.getString(R.string.genre_ation))
                12 -> stringBuilder.append(context.getString(R.string.genre_adventure))
                16 -> stringBuilder.append(context.getString(R.string.genre_animation))
                35 -> stringBuilder.append(context.getString(R.string.genre_comedy))
                80 -> stringBuilder.append(context.getString(R.string.genre_crime))
                99 -> stringBuilder.append(context.getString(R.string.genre_documentary))
                18 -> stringBuilder.append(context.getString(R.string.genre_drama))
                10751 -> stringBuilder.append(context.getString(R.string.genre_family))
                14 -> stringBuilder.append(context.getString(R.string.genre_fantasy))
                36 -> stringBuilder.append(context.getString(R.string.genre_history))
                27 -> stringBuilder.append(context.getString(R.string.genre_horror))
                10402 -> stringBuilder.append(context.getString(R.string.genre_music))
                9648 -> stringBuilder.append(context.getString(R.string.genre_mystery))
                10749 -> stringBuilder.append(context.getString(R.string.genre_romance))
                878 -> stringBuilder.append(context.getString(R.string.genre_scifi))
                10770 -> stringBuilder.append(context.getString(R.string.genre_tv_movie))
                53 -> stringBuilder.append(context.getString(R.string.genre_thriler))
                10752 -> stringBuilder.append(context.getString(R.string.genre_war))
                37 -> stringBuilder.append(context.getString(R.string.genre_western))
            }
        }

        return stringBuilder.toString().trimEnd(',', ' ').plus(".")
    }

    /**Set the appropriate fill color for the [RatingCustomView]*/
    fun getRatingColor(averageRating: Int, context: Context): Int {
        return when {
            averageRating <= 40 -> ContextCompat.getColor(context, R.color.lessThan40OrEqual)
            averageRating <= 70 -> ContextCompat.getColor(context, R.color.lessThan70OrEqual)
            averageRating <= 100 -> ContextCompat.getColor(context, R.color.lessThan100OrEqual)
            else -> ContextCompat.getColor(context, R.color.colorAccent)
        }
    }
}