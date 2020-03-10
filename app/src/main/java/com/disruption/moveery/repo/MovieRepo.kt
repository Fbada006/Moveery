package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.MovieBoundaryCallBack
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.models.Movie
import com.disruption.moveery.utils.Constants
import javax.inject.Inject

/**
 * Repository class that works with local and remote data sources.
 * This class supplies data to the ViewModel to display
 */
class MovieRepo @Inject constructor(
    private val movieLocalCache: MovieLocalCache,
    private val boundaryCallBack: MovieBoundaryCallBack
) {

    /**Get all the movies to from the local storage*/
    fun getAllMovies(): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieLocalCache.getMovieData()

        return LivePagedListBuilder(dataSourceFactory, Constants.DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallBack)
            .build()
    }
}