package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.MovieBoundaryCallBack
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.data.search.SearchedMovieDataSource
import com.disruption.moveery.models.movie.Movie
import com.disruption.moveery.models.altmovie.AltMovie
import com.disruption.moveery.utils.Constants
import kotlinx.coroutines.CoroutineScope
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

    fun getSearchedMovieList(
        queryLiveData: MutableLiveData<String>,
        scope: CoroutineScope
    ): LiveData<PagedList<AltMovie>> {

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()

        return Transformations.switchMap(queryLiveData) {
            initializedPagedListBuilder(config, it, scope).build()
        }
    }

    private fun initializedPagedListBuilder(
        config: PagedList.Config,
        query: String,
        scope: CoroutineScope
    ):
            LivePagedListBuilder<Int, AltMovie> {

        val dataSourceFactory = object : DataSource.Factory<Int, AltMovie>() {
            override fun create(): DataSource<Int, AltMovie> {
                return SearchedMovieDataSource(scope, query)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}