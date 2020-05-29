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

    init {
        movieList = repo.getSimilarMovieList(movieIdLiveData)
    }

    fun videosResource(id: Int): LiveData<Resource<List<Video>>> {
        var videoRes = MutableLiveData<Resource<List<Video>>>()
        viewModelScope.launch {
            videoRes = repo.getVideos(MOVIE_TYPE, id)
        }
        return videoRes
    }

    /**
     * Supply the movie ID to show the similar films
     */
    fun getSimilarMovies(movieId: Int) {
        movieIdLiveData.postValue(movieId)
    }
}