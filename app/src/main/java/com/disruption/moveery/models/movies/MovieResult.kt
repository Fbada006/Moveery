package com.disruption.moveery.models.movies

import com.disruption.moveery.models.movies.Movie
import com.google.gson.annotations.SerializedName

/**The result that comes back from the API call with the list of [Movie] objects*/
data class MovieResult(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)