package com.disruption.moveery.ui.search

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.search.MovieDataSource
import com.disruption.moveery.models.Movie
import com.disruption.moveery.models.SearchedMovie
import com.disruption.moveery.utils.Event
import javax.inject.Inject

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
    val movieList: LiveData<PagedList<SearchedMovie>?>
        get() = _pagedMovieList

    private fun initializedPagedListBuilder(config: PagedList.Config, query: String):
            LivePagedListBuilder<Int, SearchedMovie> {

        val dataSourceFactory = object : DataSource.Factory<Int, SearchedMovie>() {
            override fun create(): DataSource<Int, SearchedMovie> {
                return MovieDataSource(viewModelScope, query)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchMovie(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    /* The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedMovie = MutableLiveData<Event<SearchedMovie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<SearchedMovie>>
        get() = _navigateToSelectedMovie

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: SearchedMovie) {
        _navigateToSelectedMovie.value = Event(movie)
    }
}