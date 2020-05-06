package com.disruption.moveery.data.shows

import androidx.paging.PagedList
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.network.MovieApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**This class listens to when zero items were returned from the initial data request or
 * because we've reached the end of the data from the DataSource requests for more data from the
 * API such that the user has a seemingly endless scrolling feature*/
class ShowBoundaryCallBack @Inject constructor(
    private val localCache: MovieLocalCache,
    private val coroutineScope: CoroutineScope,
    private val movieRetrofitService: MovieApiService
) :
    PagedList.BoundaryCallback<TvShow>() {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        coroutineScope.launch {
            requestAndSaveShows()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: TvShow) {
        coroutineScope.launch {
            requestAndSaveShows()
        }
    }

    /**Request more shows from the API and save them*/
    private suspend fun requestAndSaveShows() {
        if (isRequestInProgress) return

        try {
            isRequestInProgress = true
            val result =
                movieRetrofitService.getDiscoverTvShowsAsync(page = lastRequestedPage).await()
            Timber.e("Result of the shows call ================= $result")
            localCache.refreshShowsCache(result)
            lastRequestedPage++
            isRequestInProgress = false
        } catch (ex: Exception) {
            Timber.e("Error in shows boundary==================== ${ex.message}")
            isRequestInProgress = false
        }
    }
}