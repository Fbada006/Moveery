package com.disruption.moveery.models.shows

data class TvShowResult(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)