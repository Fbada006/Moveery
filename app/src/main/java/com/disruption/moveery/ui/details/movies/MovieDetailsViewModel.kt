package com.disruption.moveery.ui.details.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.videos.Video
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.Constants.MOVIE_TYPE
import com.disruption.moveery.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**The viewModel to display data in the [MovieDetailsFragment]*/
class MovieDetailsViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    private val movieIdLiveData = MutableLiveData<Int>()

    /**The list of similar movies*/
    val movieList: LiveData<PagedList<Movie>>

    private val _videoRes = MutableLiveData<Resource<List<Video>>>()

    /**The resource that will supply the videos*/
    val videoRes: LiveData<Resource<List<Video>>>
        get() = _videoRes

    init {
        movieList = repo.getSimilarMovieList(movieIdLiveData)
    }

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