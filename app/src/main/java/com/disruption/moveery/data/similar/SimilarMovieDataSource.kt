package com.disruption.moveery.data.similar

import androidx.paging.PageKeyedDataSource
import com.disruption.moveery.models.altmovie.AltMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**This class will provide the list of similar movies to be displayed in the DetailsFragment.
 * This information will not be cached with room*/
class SimilarMovieDataSource @Inject constructor(private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, AltMovie>() {

    private val TAG = "SimilarMovieDataSource"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AltMovie>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AltMovie>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AltMovie>) {
        TODO("Not yet implemented")
    }

    //This is necessary because this scope is not cancelled automatically like the
    //ViewModelScope
    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}