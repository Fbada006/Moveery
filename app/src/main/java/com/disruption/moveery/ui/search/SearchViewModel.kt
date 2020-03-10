package com.disruption.moveery.ui.search

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.search.MovieDataSource
import com.disruption.moveery.models.Movie

/**[ViewModel] to supply data to the [SearchFragment]*/
class SearchViewModel : ViewModel() {
    val TAG = "SearchViewModel"

    private val queryLiveData = MutableLiveData<String>()

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

    private val _pagedMovieList = Transformations.switchMap(queryLiveData) {
        initializedPagedListBuilder(config, it).build()
    }

    // The external LiveData interface to the list is immutable, so only this class can modify
    val pagedMovieList: LiveData<PagedList<Movie>?>
        get() = _pagedMovieList

    private fun initializedPagedListBuilder(config: PagedList.Config, query: String):
            LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MovieDataSource(viewModelScope, query)
            }
        }
        return LivePagedListBuilder<Int, Movie>(dataSourceFactory, config)
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchMovie(queryString: String) {
        queryLiveData.postValue(queryString)
    }
}