package com.disruption.moveery.ui.details.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.disruption.moveery.models.movies.movie.Movie
import com.disruption.moveery.repo.MovieRepo
import javax.inject.Inject

/**The viewModel to display data in the [MovieDetailsFragment]*/
class MovieDetailsViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    private val movieIdLiveData = MutableLiveData<Int>()

    val movieList: LiveData<PagedList<Movie>>

    init {
        movieList = repo.getSimilarMovieList(movieIdLiveData)
    }

    /**
     * Supply the movie ID to show the similar films
     */
    fun getSimilarMovies(movieId: Int) {
        movieIdLiveData.postValue(movieId)
    }
}