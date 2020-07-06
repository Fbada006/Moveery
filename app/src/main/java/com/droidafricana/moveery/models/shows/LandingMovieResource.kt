package com.droidafricana.moveery.models.shows

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * RepoSearchResult from a shows load, which contains LiveData<List<TvShow>> holding show data,
 * and a LiveData<String> of network error state.
 */
data class LandingShowResource(
    val data: LiveData<PagedList<TvShow>>,
    val loading: LiveData<Boolean>,
    val errors: LiveData<String>
)