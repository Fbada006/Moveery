package com.disruption.moveery.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.disruption.moveery.models.Movie
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.Event

/**The class holding the loading status of the API call*/
enum class MovieApiStatus { LOADING, ERROR, DONE }

/**The view model that handles the UI and logic for the [LandingFragment]*/
class LandingViewModel(repo: MovieRepo) : ViewModel() {

    /** The internal MutableLiveData that stores the status of the most recent request */
    private val _status = MutableLiveData<MovieApiStatus>()

    /**The external immutable LiveData for the request status*/
    val status: LiveData<MovieApiStatus>
        get() = _status

    /** The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedMovie = MutableLiveData<Event<Movie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<Movie>>
        get() = _navigateToSelectedMovie

//    /**
//     * Call getMovieList() on init so we can display status immediately.
//     */
//    init {
//        getMovieList(repo)
//    }
//
//    /**
//     * Gets the movie list information from the MoviesRepo
//     */
//    private fun getMovieList(repo: MovieRepo) {
//        viewModelScope.launch {
//            _status.value = MovieApiStatus.LOADING
//            try {
//                repo.getAllMovies()
//                _status.value = MovieApiStatus.DONE
//            } catch (ex: Exception) {
//                _status.value = MovieApiStatus.ERROR
//            }
//        }
//    }

    /**These are all the movies queried from the db as a PagedList*/
    val movieList = repo.getAllMovies()

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = Event(movie)
    }

//    companion object {
//        /**Factory to create this [LandingViewModel]*/
//        val FACTORY = singleArgViewModelFactory(::LandingViewModel)
//    }
}