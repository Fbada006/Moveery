package com.disruption.moveery.utils

/**Handles getting the genres of the selected [Movie]*/
object DetailsHelper {
    /**Returns the genres of the movie from the given [genreIds]*/
    fun getGenres(genreIds: List<Int>): String {
        val stringBuilder = StringBuilder()
        genreIds.forEach {
            when (it) {
                28 -> stringBuilder.append("Action, ")
                12 -> stringBuilder.append("Adventure, ")
                16 -> stringBuilder.append("Animation, ")
                35 -> stringBuilder.append("Comedy, ")
                80 -> stringBuilder.append("Crime, ")
                99 -> stringBuilder.append("Documentary, ")
                18 -> stringBuilder.append("Drama, ")
                10751 -> stringBuilder.append("Family, ")
                14 -> stringBuilder.append("Fantasy, ")
                36 -> stringBuilder.append("History, ")
                27 -> stringBuilder.append("Horror, ")
                10402 -> stringBuilder.append("Music, ")
                9648 -> stringBuilder.append("Mystery, ")
                10749 -> stringBuilder.append("Romance, ")
                878 -> stringBuilder.append("Science Fiction, ")
                10770 -> stringBuilder.append("TV Movie, ")
                53 -> stringBuilder.append("Thriller, ")
                10752 -> stringBuilder.append("War, ")
                37 -> stringBuilder.append("Western, ")
            }
        }

        return stringBuilder.toString().trimEnd(',', ' ').plus(".")
    }
}