package com.droidafricana.moveery.data.shows.search

import androidx.paging.PageKeyedDataSource
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

/**The DataSource that supplies the searched list*/
class SearchedShowDataSource(
    private val scope: CoroutineScope,
    private val query: String
) : PageKeyedDataSource<Int, TvShow>() {

    private val movieApiService = MovieApi.movieRetrofitService

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {
        scope.launch {
            try {
                val response = movieApiService.getTvShowsByKeyword(
                    query = query,
                    page = 1
                )

                if (response.isSuccessful) {
                    val result = response.body()
                    val showList = result?.showsList
                    callback.onResult(showList ?: listOf(), null, 2)
                }
            } catch (ex: Exception) {
                Timber.e("Failed to fetch initial data with error: ------------ $ex")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        scope.launch {
            try {
                val response = movieApiService.getTvShowsByKeyword(
                    query = query,
                    page = params.key
                )

                if (response.isSuccessful) {
                    val result = response.body()
                    val showList = result?.showsList
                    callback.onResult(showList ?: listOf(), params.key + 1)
                } else {
                    Timber.e("Response is not successful----------- ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                Timber.e(
                    "Failed to fetch after data with error: ------------ ${ex.localizedMessage}"
                )
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        //Ignore this for now
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}