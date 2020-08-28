package com.droidafricana.moveery.ui.details.similardetails.similarmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.Event
import com.droidafricana.moveery.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class SimilarMovieDetailsViewModel @Inject constructor(private val repo: MovieRepo) :
    ViewModel() {

    /*The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedMovie = MutableLiveData<Event<Movie>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedMovie: LiveData<Event<Movie>>
        get() = _navigateToSelectedMovie

    private val _videoRes = MutableLiveData<Resource<List<Video>>>()

    /**The resource that will supply the videos*/
    val videoRes: LiveData<Resource<List<Video>>>
        get() = _videoRes

    /**Method that gets the videos*/
    fun getVideosResource(id: Int) {
        viewModelScope.launch {
            _videoRes.value = repo.getVideos(Constants.MOVIE_TYPE, id).value
        }
    }

    /**Insert a movie to favourites*/
    fun insertMovieIntoFav(movie: Movie) {
        viewModelScope.launch { repo.insertMovieToFav(movie) }
    }

    /**Delete a movie from favourites*/
    fun deleteMoveFromFav(id: Int) {
        viewModelScope.launch { repo.deleteMovieFromFav(id) }
    }

    /**If the movie is null, it is not in fav and so the like button should be in the un-liked state
     * and vice-versa
     **/
    fun isMovieInFav(id: Int) = repo.getMovieById(id)

    /**Called when a user clicks on a movie*/
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = Event(movie)
    }
}
