package com.disruption.moveery.data.search

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.disruption.moveery.models.SearchedMovie
import com.disruption.moveery.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieDataSource(private val scope: CoroutineScope, private val query: String) :
    PageKeyedDataSource<Int, SearchedMovie>() {
    val TAG = "MovieDataSource"

    private val movieApiService = MovieApi.movieRetrofitService

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchedMovie>
    ) {
        scope.launch {
            try {
                val response = movieApiService.getMoviesByKeyword(query = query, page = 1)

                if (response.isSuccessful) {
                    val result = response.body()
                    val movieList = result?.movieList
                    callback.onResult(movieList ?: listOf(), null, 2)
                }

            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch initial data with error: ------------ $ex")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchedMovie>) {
        scope.launch {
            try {
                val response = movieApiService.getMoviesByKeyword(query = query, page = params.key)

                if (response.isSuccessful) {
                    val result = response.body()
                    val movieList = result?.movieList
                    callback.onResult(movieList ?: listOf(), params.key + 1)
                }
            } catch (ex: Exception) {
                Log.e(
                    TAG,
                    "Failed to fetch after data with error: ------------ ${ex.localizedMessage}"
                )
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchedMovie>) {
        //Ignore this shite for now
    }

    @ExperimentalCoroutinesApi
    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}