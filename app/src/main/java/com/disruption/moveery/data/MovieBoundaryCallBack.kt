package com.disruption.moveery.data

import android.util.Log
import androidx.paging.PagedList
import com.disruption.moveery.models.Movie
import com.disruption.moveery.network.MovieApi
import com.disruption.moveery.repo.MovieLocalCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**This class listens to when zero items were returned from the initial data request or
 * because we've reached the end of the data from the DataSource requests for more data from the
 * API such that the user has a seemingly endless scrolling feature*/
class MovieBoundaryCallBack(
    private val localCache: MovieLocalCache,
    private val coroutineScope: CoroutineScope
) :
    PagedList.BoundaryCallback<Movie>() {
    val TAG = "MovieBoundaryCallBack"

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        coroutineScope.launch {
            requestAndSaveMovies()
            Log.e(TAG, "On Zero Items loaded!-------------`: $lastRequestedPage ")
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        coroutineScope.launch {
            requestAndSaveMovies()
            Log.e(TAG, "On item ended loaded!-------------`: $lastRequestedPage ")
        }
    }

    /**Request more movies from the API and save them*/
    private suspend fun requestAndSaveMovies() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        val result =
            MovieApi.movieRetrofitService.getDiscoverMoviesAsync(page = lastRequestedPage).await()
        localCache.insertMovies(result)
        lastRequestedPage++
        isRequestInProgress = false
    }
}