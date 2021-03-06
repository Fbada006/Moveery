package com.droidafricana.moveery.network

import com.droidafricana.moveery.models.movies.MovieResult
import com.droidafricana.moveery.models.shows.TvShowResult
import com.droidafricana.moveery.models.videos.VideoResult
import com.droidafricana.moveery.utils.Constants.STRAW
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
        @Query("include_adult") include_adult: Boolean = false,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("api_key") apiKey: String = STRAW
    ): Deferred<MovieResult>

    /**Returns similar movies on the details page*/
    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("api_key") apiKey: String = STRAW
    ): Response<MovieResult>

    /**Searches the movie*/
    @GET("4/search/movie")
    suspend fun getMoviesByKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("api_key") apiKey: String = STRAW
    ): Response<MovieResult>

    /**Gets shows on the shows landing page*/
    @GET("discover/tv")
    fun getDiscoverTvShowsAsync(
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("api_key") apiKey: String = STRAW
    ): Deferred<TvShowResult>

    /**Returns similar TV shows on the details page*/
    @GET("3/tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") showId: Int,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("api_key") apiKey: String = STRAW
    ): Response<TvShowResult>

    /**Searches the TV Show*/
    @GET("4/search/tv")
    suspend fun getTvShowsByKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("api_key") apiKey: String = STRAW
    ): Response<TvShowResult>

    /**Returns videos for a movie*/
    @GET("3/movie/{movie_id}/videos")
    fun getMovieVideosAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = STRAW
    ): Deferred<VideoResult>

    /**Returns videos for a TV Show*/
    @GET("3/tv/{tv_id}/videos")
    fun getTvShowVideosAsync(
        @Path("tv_id") showId: Int,
        @Query("api_key") apiKey: String = STRAW
    ): Deferred<VideoResult>
}