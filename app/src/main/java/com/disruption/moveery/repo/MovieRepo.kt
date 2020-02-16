package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.models.Movie
import com.disruption.moveery.network.MovieApi
import com.disruption.moveery.utils.Constants

/**This class is the data controller. It queries the API and saves data to the DB
 * then offers it back to the viewModels*/
class MovieRepo(private val movieRoomDatabase: MovieRoomDatabase) {
    /**Room executes all queries on a separate thread.*/
    fun getAllMovies(): LiveData<PagedList<Movie>> {
        val source = movieRoomDatabase.movieDao.getAllMovies()
        return LivePagedListBuilder(source, Constants.DATABASE_PAGE_SIZE).build()
    }

    /**Use coroutines to get new movies and save them into the database*/
    suspend fun refreshMovies() {
        val movieResult = MovieApi.movieRetrofitService.getDiscoverMoviesAsync().await()
        movieRoomDatabase.movieDao.insertAllMovies(*movieResult.movieList.toTypedArray())
    }
}