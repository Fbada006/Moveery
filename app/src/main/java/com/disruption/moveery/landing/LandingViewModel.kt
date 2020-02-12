package com.disruption.moveery.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.singleArgViewModelFactory
import kotlinx.coroutines.launch

enum class MovieApiStatus { LOADING, ERROR, DONE }

class LandingViewModel(repo: MovieRepo) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MovieApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<MovieApiStatus>
        get() = _status

    /**
     * Call getMovieList() on init so we can display status immediately.
     */
    init {
        getMovieList(repo)
    }

    /**
     * Gets the movie list information from the MoviesRepo
     */
    private fun getMovieList(repo: MovieRepo) {
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                repo.refreshMovies()
                _status.value = MovieApiStatus.DONE
            } catch (ex: Exception) {
                _status.value = MovieApiStatus.ERROR
            }
        }
    }

    /**These are all the movies queried from the db*/
    val movieList = repo.allMovies

    companion object {
        /**Factory to create this [LandingViewModel]*/
        val FACTORY = singleArgViewModelFactory(::LandingViewModel)
    }
}