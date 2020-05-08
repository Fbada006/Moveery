package com.disruption.moveery.network

import com.disruption.moveery.models.movies.altmovie.AltResult
import com.disruption.moveery.models.movies.movie.MovieResult
import com.disruption.moveery.models.shows.TvShowResult
import com.disruption.moveery.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A public interface that exposes the api methods
 */
interface MovieApiService {

    /**Returns movies on the landing page*/
    @GET("discover/movie")
    fun getDiscoverMoviesAsync(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<MovieResult>

    /**Returns similar movies on the details page*/
    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<AltResult>

    /**Searches the movie*/
    @GET("4/search/movie")
    suspend fun getMoviesByKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") boolean: Boolean = false,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<AltResult>

    /**Gets movies on the [ShowsLandingPage]*/
    @GET("discover/tv")
    fun getDiscoverTvShowsAsync(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<TvShowResult>

    /**Returns similar TV shows on the details page*/
    @GET("3/tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") showId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<TvShowResult>

    /**Searches the TV Show*/
    @GET("4/search/tv")
    suspend fun getTvShowsByKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") boolean: Boolean = false,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<TvShowResult>
}