package com.disruption.moveery.data

import androidx.paging.DataSource
import com.disruption.moveery.models.favourites.movies.FavMovie
import com.disruption.moveery.models.favourites.shows.FavShow
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.movies.MovieResult
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.models.shows.TvShowResult

/**The interface to expose methods of interacting with the Local cache*/
interface IMovieLocalCache {

    /**Room executes all queries on a separate thread.*/
    fun getMovieData(): DataSource.Factory<Int, Movie>

    /**Get all the shows*/
    fun getShowsData(): DataSource.Factory<Int, TvShow>

    /**Get all the fav movies*/
    fun getFavMovieData(): DataSource.Factory<Int, FavMovie>

    /**Get all the fav shows*/
    fun getFavShowsData(): DataSource.Factory<Int, FavShow>

    /**Insert the movies into the database on a background thread*/
    suspend fun refreshMoviesCache(movieMovieResult: MovieResult)

    /**Insert TV shows*/
    suspend fun refreshShowsCache(tvShowResult: TvShowResult)
}