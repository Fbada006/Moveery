package com.disruption.moveery.data

import androidx.lifecycle.LiveData
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

    /**Insert a single movie to favourites*/
    suspend fun insertMovieToFav(movie: FavMovie)

    /**Insert a show to favourites*/
    suspend fun insertShowToFav(show: FavShow)

    /**Delete movie from fav*/
    suspend fun deleteMovieFromFav(id: Int)

    /**Delete show from fav*/
    suspend fun deleteShowFromFav(id: Int)

    /**Get a movie based on its id*/
    fun getMovieById(id: Int?): LiveData<FavMovie?>

    /**Get a show based on its id*/
    fun getShowById(id: Int): LiveData<FavShow>
}