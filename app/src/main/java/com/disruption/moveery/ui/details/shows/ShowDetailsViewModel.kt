package com.disruption.moveery.ui.details.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.repo.MovieRepo
import javax.inject.Inject

/**The ViewModel that handles displaying data on the [ShowDetailsFragment]*/
class ShowDetailsViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    private val showIdLiveData = MutableLiveData<Int>()

    val showList: LiveData<PagedList<TvShow>>

    init {
        showList = repo.getSimilarShowsList(showIdLiveData)
    }

    /**
     * Supply the show ID to show the similar shows
     */
    fun getSimilarShows(showId: Int) {
        showIdLiveData.postValue(showId)
    }
}
