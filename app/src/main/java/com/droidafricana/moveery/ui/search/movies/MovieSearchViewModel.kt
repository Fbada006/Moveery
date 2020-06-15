package com.droidafricana.moveery.ui.search.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Event
import javax.inject.Inject

/**[ViewModel] to supply data to the [MovieSearchFragment]*/
class MovieSearchViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val movieList =
        repo.getSearchedMovieList(queryLiveData)

    /**
     * Search a movie based on a query string.
     */
    fun searchMovie(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    private val _navigateToSelectedMovie = MutableLiveData<Event<Movie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<Movie>>
        get() = _navigateToSelectedMovie

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = Event(movie)
    }
}