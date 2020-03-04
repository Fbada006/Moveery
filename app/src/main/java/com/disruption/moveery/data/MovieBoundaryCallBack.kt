package com.disruption.moveery.data

import androidx.paging.PagedList
import com.disruption.moveery.models.Movie
import com.disruption.moveery.network.MovieApi
import kotlinx.coroutines.*

/**This class listens to when zero items were returned from the initial data request or
 * because we've reached the end of the data from the DataSource requests for more data from the
 * API such that the user has a seemingly endless scrolling feature*/
class MovieBoundaryCallBack(
    private val localCache: MovieLocalCache,
    private val coroutineScope: CoroutineScope
) :
    PagedList.BoundaryCallback<Movie>() {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        coroutineScope.launch {
            requestAndSaveMovies()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        coroutineScope.launch {
            requestAndSaveMovies()
        }
    }

    /**Request more movies from the API and save them*/
    private suspend fun requestAndSaveMovies() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        val result =
            MovieApi.movieRetrofitService.getDiscoverMoviesAsync(page = lastRequestedPage).await()
        localCache.refreshMoviesCache(result)
        lastRequestedPage++
        isRequestInProgress = false
    }
}