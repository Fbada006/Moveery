package com.disruption.moveery.ui.favourites.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

/**The view model that handles the UI and logic for the [FavouriteMoviesFragment]*/
class FavMoviesViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    /*The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedMovie = MutableLiveData<Event<Movie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<Movie>>
        get() = _navigateToSelectedMovie

    /**These are all the fav movies queried from the db as a PagedList*/
    val movieList = repo.getAllFavMovies()

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = Event(movie)
    }

    /**Nuke the favourites in movies*/
    fun nukeMovieFavourites() {
        viewModelScope.launch { repo.nukeMovieFavourites() }
    }
}