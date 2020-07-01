package com.droidafricana.moveery.models.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * RepoSearchResult from a movies load, which contains LiveData<List<Movie>> holding movie data,
 * and a LiveData<String> of network error state.
 */
data class LandingMovieResult(
    val data: LiveData<PagedList<Movie>>,
    val loading: LiveData<Boolean>,
    val errors: LiveData<String>
)