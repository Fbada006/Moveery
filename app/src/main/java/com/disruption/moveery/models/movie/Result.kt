package com.disruption.moveery.models.movie

import com.disruption.moveery.models.movie.Movie
import com.squareup.moshi.Json

/**The result that comes back from the API call with the list of [Movie] objects*/
data class Result(
    val page: Int,
    @Json(name = "results")
    val movieList: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)