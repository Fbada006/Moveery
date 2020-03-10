package com.disruption.moveery.models

import com.google.gson.annotations.SerializedName

/**The result that comes back from the API call with the list of [SearchedMovieMovie] objects*/
data class SearchedResult(
    val page: Int,
    @SerializedName("results")
    val movieList: List<SearchedMovie>,
    val total_pages: Int,
    val total_results: Int
)