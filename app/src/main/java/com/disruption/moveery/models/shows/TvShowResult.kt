package com.disruption.moveery.models.shows

import com.google.gson.annotations.SerializedName

data class TvShowResult(
    val page: Int,
    @SerializedName("results")
    val showsList: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)