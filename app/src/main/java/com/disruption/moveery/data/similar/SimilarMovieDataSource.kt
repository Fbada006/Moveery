package com.disruption.moveery.data.similar

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.disruption.moveery.models.movies.altmovie.AltMovie
import com.disruption.moveery.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**This class will provide the list of similar movies to be displayed in the DetailsFragment.
 * This information will not be cached with room*/
class SimilarMovieDataSource(
    private val scope: CoroutineScope,
    private val movieId: Int
) :
    PageKeyedDataSource<Int, AltMovie>() {

    private val TAG = "SimilarMovieDataSource"

    private val movieApiService = MovieApi.movieRetrofitService

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AltMovie>
    ) {
        scope.launch {
            try {
                val response = movieApiService.getSimilarMovies(
                    movieId = movieId,
                    page = 1
                )

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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AltMovie>) {
        scope.launch {
            try {
                val response = movieApiService.getSimilarMovies(
                    movieId = movieId,
                    page = params.key
                )

                if (response.isSuccessful) {
                    val result = response.body()
                    val movieList = result?.movieList
                    callback.onResult(movieList ?: listOf(), params.key + 1)
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch initial data with error: ------------ $ex")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AltMovie>) {
        //Ignore for now
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}