package com.disruption.moveery.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruption.moveery.models.altmovie.AltMovie
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.Event
import javax.inject.Inject

/**[ViewModel] to supply data to the [SearchFragment]*/
class SearchViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val movieList =
        repo.getSearchedMovieList(queryLiveData, viewModelScope)

    /**
     * Search a movie based on a query string.
     */
    fun searchMovie(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    private val _navigateToSelectedMovie = MutableLiveData<Event<AltMovie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<AltMovie>>
        get() = _navigateToSelectedMovie

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: AltMovie) {
        _navigateToSelectedMovie.value = Event(movie)
    }
}