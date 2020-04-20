package com.disruption.moveery.ui.details.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.disruption.moveery.models.movies.altmovie.AltMovie
import com.disruption.moveery.repo.MovieRepo
import javax.inject.Inject

/**The viewModel to display data in the [MovieDetailsFragment]*/
class MovieDetailsViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {
    val TAG = "DetailsViewModel"

    private val movieIdLiveData = MutableLiveData<Int>()

    val movieList: LiveData<PagedList<AltMovie>>

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