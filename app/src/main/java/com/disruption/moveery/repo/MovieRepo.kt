package com.disruption.moveery.repo

import com.disruption.moveery.db.MovieRoomDatabase
import com.disruption.moveery.network.MovieApi

/**This class is the data controller. It queries the API and saves data to the DB
 * then offers it back to the viewModels*/
class MovieRepo(private val movieRoomDatabase: MovieRoomDatabase) {
    /**Room executes all queries on a separate thread.*/
    val allMovies = movieRoomDatabase.movieDao.getAllMovies()

    /**Use coroutines to get new movies and save them into the database*/
    suspend fun refreshMovies() {
        val movieResult = MovieApi.movieRetrofitService.getDiscoverMoviesAsync().await()
        movieRoomDatabase.movieDao.insertAllMovies(*movieResult.movieList.toTypedArray())
    }
}