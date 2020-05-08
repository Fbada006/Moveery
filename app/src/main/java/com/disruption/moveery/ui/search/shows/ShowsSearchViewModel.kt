package com.disruption.moveery.ui.search.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.Event
import javax.inject.Inject

/**Supply list of [TvShow] objects that the user has searched for*/
class ShowsSearchViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val showList =
        repo.getSearchedShowList(queryLiveData)

    /**
     * Search a show based on a query string.
     */
    fun searchShow(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    private val _navigateToSelectedShow = MutableLiveData<Event<TvShow>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedShow: LiveData<Event<TvShow>>
        get() = _navigateToSelectedShow

    /**Called when a user clicks on a show*/
    fun displayShowDetails(tvShow: TvShow) {
        _navigateToSelectedShow.value = Event(tvShow)
    }
}
