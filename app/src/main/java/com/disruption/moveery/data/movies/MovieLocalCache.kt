package com.disruption.moveery.data.movies

import androidx.paging.DataSource
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.models.movies.movie.Movie
import com.disruption.moveery.models.movies.movie.MovieResult
import javax.inject.Inject

/**This class gets the data from the API and saves it to the offline db*/
class MovieLocalCache @Inject constructor(private val movieRoomDatabase: MovieRoomDatabase) {

    /**Room executes all queries on a separate thread.*/
    fun getMovieData(): DataSource.Factory<Int, Movie> = movieRoomDatabase.movieDao.getAllMovies()

    /**Insert the movies into the database on a background thread*/
    suspend fun refreshMoviesCache(movieMovieResult: MovieResult) {
        movieRoomDatabase.movieDao.insertAllMovies(movieMovieResult.movieList)
    }
}