package com.droidafricana.moveery.ui.landing.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Event
import javax.inject.Inject

class ShowsLandingViewModel @Inject constructor(repo: MovieRepo) : ViewModel() {

    /* The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedShow = MutableLiveData<Event<TvShow>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedShow: LiveData<Event<TvShow>>
        get() = _navigateToSelectedShow

    /**These are all the shows queried from the db as a PagedList*/
    val showsList = repo.getAllShows()

    /**Called when a user clicks on a show*/
    fun displayShowDetails(show: TvShow) {
        _navigateToSelectedShow.value = Event(show)
    }
}
