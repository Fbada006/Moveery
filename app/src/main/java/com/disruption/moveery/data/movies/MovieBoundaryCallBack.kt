package com.disruption.moveery.data.movies

import androidx.paging.PagedList
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.network.MovieApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**This class listens to when zero items were returned from the initial data request or
 * because we've reached the end of the data from the DataSource requests for more data from the
 * API such that the user has a seemingly endless scrolling feature*/
class MovieBoundaryCallBack @Inject constructor(
    private val localCache: MovieLocalCache,
    private val coroutineScope: CoroutineScope,
    private val movieRetrofitService: MovieApiService
) : PagedList.BoundaryCallback<Movie>() {

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

        try {
            isRequestInProgress = true
            val result =
                movieRetrofitService.getDiscoverMoviesAsync(
                    page = lastRequestedPage
                ).await()
            localCache.refreshMoviesCache(result)
            lastRequestedPage++
            isRequestInProgress = false
        } catch (ex: Exception) {
            isRequestInProgress = false
        }
    }
}