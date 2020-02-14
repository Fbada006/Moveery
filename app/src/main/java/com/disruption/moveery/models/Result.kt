package com.disruption.moveery.models

import com.squareup.moshi.Json

data class Result(
    val page: Int,
    @Json(name = "results")
    val movieList: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)