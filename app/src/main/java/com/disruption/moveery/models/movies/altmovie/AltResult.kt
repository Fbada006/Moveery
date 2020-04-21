package com.disruption.moveery.models.movies.altmovie

import com.google.gson.annotations.SerializedName

//TODO: Get rid of this extra POJO
/**The result that comes back from the API call with the list of [AltMovie] objects*/
data class AltResult(
    val page: Int,
    @SerializedName("results")
    val movieList: List<AltMovie>,
    val total_pages: Int,
    val total_results: Int
)