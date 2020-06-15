package com.droidafricana.moveery.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.droidafricana.moveery.models.favourites.movies.FavMovie
import com.droidafricana.moveery.models.favourites.shows.FavShow
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.movies.MovieResult
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.shows.TvShowResult

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

    /**Delete all movies from fav*/
    suspend fun nukeMovieFavourites()

    /**Delete all shows from fav*/
    suspend fun nukeShowFavourites()

    /**Get a movie based on its id*/
    fun getMovieById(id: Int?): LiveData<FavMovie?>

    /**Get a show based on its id*/
    fun getShowById(id: Int?): LiveData<FavShow?>
}