package com.droidafricana.moveery.ui.landing.shows

import androidx.lifecycle.*
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

    //The landing show result from the repo
    private val landingShowResult = liveData { emit(repo.getLandingShows()) }

    /**These are all the shows queried from the db as a PagedList*/
    val showsList = Transformations.switchMap(landingShowResult) {
        it.data
    }

    /**This is the loading status*/
    val errors = Transformations.switchMap(landingShowResult) {
        it.errors
    }

    /**Any errors*/
    val loading = Transformations.switchMap(landingShowResult) {
        it.loading
    }

    /**Called when a user clicks on a show*/
    fun displayShowDetails(show: TvShow) {
        _navigateToSelectedShow.value = Event(show)
    }
}
