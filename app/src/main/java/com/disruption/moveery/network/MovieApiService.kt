package com.disruption.moveery.network

import com.disruption.moveery.models.movie.Result
import com.disruption.moveery.models.altmovie.AltResult
import com.disruption.moveery.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A public interface that exposes the [getDiscoverMoviesAsync] method
 */
interface MovieApiService {

    /**Returns movies on the landing page*/
    @GET("discover/{type}")
    fun getDiscoverMoviesAsync(
        @Path("type") type: String = "movie",
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<Result>

    /**Returns similar movies on the details page*/
    @GET("/movie/{movie_id}/similar")
    suspend fun getSimilarMoviesAsync(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<Result>

    /**Searches the movie*/
    @GET("search/movie")
    suspend fun getMoviesByKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") boolean: Boolean = false,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<AltResult>
}