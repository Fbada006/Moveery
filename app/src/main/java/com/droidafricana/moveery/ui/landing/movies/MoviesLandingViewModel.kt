package com.droidafricana.moveery.ui.landing.movies

import androidx.lifecycle.*
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Event
import javax.inject.Inject

/**The view model that handles the UI and logic for the [MoviesLandingFragment]*/
class MoviesLandingViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    /*The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedMovie = MutableLiveData<Event<Movie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<Movie>>
        get() = _navigateToSelectedMovie

    /*The landing result from the repo*/
    private val landingMovieResult = liveData {
        emit(repo.getLandingMovies())
    }

    /**These are all the movies queried from the db as a PagedList*/
    val movieList = Transformations.switchMap(landingMovieResult) {
        it.data
    }

    /**This is the loading status*/
    val errors = Transformations.switchMap(landingMovieResult) {
        it.errors
    }

    /**Any errors*/
    val loading = Transformations.switchMap(landingMovieResult) {
        it.loading
    }

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = Event(movie)
    }
}