package com.disruption.moveery.data.movies.search

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.disruption.moveery.models.movies.altmovie.AltMovie
import com.disruption.moveery.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

//TODO: Use Firebase logging here
class SearchedMovieDataSource(private val scope: CoroutineScope,
                              private val query: String) :
    PageKeyedDataSource<Int, AltMovie>() {
    private val TAG = "SearchedMovieDataSource"

    private val movieApiService = MovieApi.movieRetrofitService

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AltMovie>
    ) {
        scope.launch {
            try {
                val response = movieApiService.getMoviesByKeyword(query = query, page = 1)

                if (response.isSuccessful) {
                    val result = response.body()
                    val movieList = result?.movieList
                    callback.onResult(movieList ?: listOf(), null, 2)
                    Log.e(TAG, "Load initial for search a success: -----------$movieList")

                } else {
                    Log.e(TAG, "Response is not successful----------- ${response.message()} ")
                }

            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch initial data with error: ------------ $ex")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AltMovie>) {
        scope.launch {
            try {
                val response = movieApiService.getMoviesByKeyword(query = query, page = params.key)

                if (response.isSuccessful) {
                    val result = response.body()
                    val movieList = result?.movieList
                    callback.onResult(movieList ?: listOf(), params.key + 1)
                } else {
                    Log.e(TAG, "Response is not successful----------- ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                Log.e(
                    TAG,
                    "Failed to fetch after data with error: ------------ ${ex.localizedMessage}"
                )
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AltMovie>) {
        //Ignore this shite for now
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}