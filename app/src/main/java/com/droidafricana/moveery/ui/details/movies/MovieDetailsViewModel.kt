package com.droidafricana.moveery.ui.details.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Constants.MOVIE_TYPE
import com.droidafricana.moveery.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**The viewModel to display data in the [MovieDetailsFragment]*/
class MovieDetailsViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    private val movieIdLiveData = MutableLiveData<Int>()

    /**The list of similar movies*/
    val movieList = repo.getSimilarMovieList(movieIdLiveData)

    private val _videoRes = MutableLiveData<Resource<List<Video>>>()

    /**The resource that will supply the videos*/
    val videoRes: LiveData<Resource<List<Video>>>
        get() = _videoRes

    /**Method that gets the videos*/
    fun getVideosResource(id: Int) {
        viewModelScope.launch {
            _videoRes.value = repo.getVideos(MOVIE_TYPE, id).value
        }
    }

    /**
     * Supply the movie ID to show the similar films
     */
    fun getSimilarMovies(movieId: Int) {
        movieIdLiveData.postValue(movieId)
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
}