package com.disruption.moveery.data.shows.similar

import androidx.paging.PageKeyedDataSource
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**This class will provide the list of similar shows to be displayed in the ShowDetailsFragment.
 * This information will not be cached with room*/
class SimilarShowDataSource(
    private val scope: CoroutineScope,
    private val showId: Int
) :
    PageKeyedDataSource<Int, TvShow>() {

    private val movieApiService = MovieApi.movieRetrofitService

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {
        scope.launch {
            try {
                val response = movieApiService.getSimilarTvShows(
                    showId = showId,
                    page = 1
                )

                if (response.isSuccessful) {
                    val result = response.body()
                    val showsList = result?.showsList
                    callback.onResult(showsList ?: listOf(), null, 2)
                }
            } catch (ex: Exception) {
                Timber.e("Failed to fetch initial data with error: ------------ $ex")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        scope.launch {
            try {
                val response = movieApiService.getSimilarTvShows(
                    showId = showId,
                    page = params.key
                )

                if (response.isSuccessful) {
                    val result = response.body()
                    val showList = result?.showsList
                    callback.onResult(showList ?: listOf(), params.key + 1)
                }
            } catch (ex: Exception) {
                Timber.e("Failed to fetch initial data with error: ------------ $ex")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        //Ignore
    }
}