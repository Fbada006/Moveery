package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.MovieBoundaryCallBack
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.models.Movie
import com.disruption.moveery.utils.Constants
import kotlinx.coroutines.CoroutineScope

/**
 * Repository class that works with local and remote data sources.
 * This class supplies data to the ViewModel to display
 */
class MovieRepo(
    private val movieLocalCache: MovieLocalCache,
    private val coroutineScope: CoroutineScope
) {

    /**Get all the movies to from the local storage*/
    fun getAllMovies(): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieLocalCache.getMovieData()

        val boundaryCallBack = MovieBoundaryCallBack(movieLocalCache, coroutineScope)

        return LivePagedListBuilder(dataSourceFactory, Constants.DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallBack)
            .build()
    }
}