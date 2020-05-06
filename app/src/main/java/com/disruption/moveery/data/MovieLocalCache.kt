package com.disruption.moveery.data

import androidx.paging.DataSource
import com.disruption.moveery.models.movies.movie.Movie
import com.disruption.moveery.models.movies.movie.MovieResult
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.models.shows.TvShowResult
import javax.inject.Inject

/**This class gets the data from the API and saves it to the offline db*/
class MovieLocalCache @Inject constructor(private val movieRoomDatabase: MovieRoomDatabase) {

    /**Room executes all queries on a separate thread.*/
    fun getMovieData(): DataSource.Factory<Int, Movie> = movieRoomDatabase.movieDao.getAllMovies()

    /**Get all the shows*/
    fun getShowsData(): DataSource.Factory<Int, TvShow> = movieRoomDatabase.movieDao.getAllShows()

    /**Insert the movies into the database on a background thread*/
    suspend fun refreshMoviesCache(movieMovieResult: MovieResult) {
        movieRoomDatabase.movieDao.insertAllMovies(movieMovieResult.movieList)
    }

    /**Insert TV shows*/
    suspend fun refreshShowsCache(tvShowResult: TvShowResult) {
        movieRoomDatabase.movieDao.insertAllShows(tvShowResult.showsList)
    }
}