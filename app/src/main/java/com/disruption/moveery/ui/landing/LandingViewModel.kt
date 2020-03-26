package com.disruption.moveery.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.disruption.moveery.models.movie.Movie
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.Event
import javax.inject.Inject

/**The view model that handles the UI and logic for the [LandingFragment]*/
class LandingViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    /* The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedMovie = MutableLiveData<Event<Movie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<Movie>>
        get() = _navigateToSelectedMovie

    /**These are all the movies queried from the db as a PagedList*/
    val movieList = repo.getAllMovies()

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = Event(movie)
    }

}