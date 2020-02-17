package com.disruption.moveery.repo

import androidx.paging.DataSource
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.models.Movie
import com.disruption.moveery.models.Result

/**This class gets the data from the API and saves it to the offline db*/
class MovieLocalCache(private val movieRoomDatabase: MovieRoomDatabase) {

    /**Room executes all queries on a separate thread.*/
    fun getMovieData(): DataSource.Factory<Int, Movie> = movieRoomDatabase.movieDao.getAllMovies()

    /**Insert the movies into the database on a background thread*/
    suspend fun insertMovies(movieResult: Result) {
        movieRoomDatabase.movieDao.insertAllMovies(movieResult.movieList)
    }
}