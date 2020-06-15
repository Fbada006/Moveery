package com.droidafricana.moveery.ui.favourites.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

/**The view model that handles the UI and logic for the [FavouriteShowsFragment*/
class FavShowsViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    /*The internal MutableLiveData that stores the event of a click input */
    private val _navigateToSelectedShow = MutableLiveData<Event<TvShow>>()

    /**The external immutable LiveData for the click event*/
    val navigateToSelectedShow: LiveData<Event<TvShow>>
        get() = _navigateToSelectedShow

    /**These are all the fav shows queried from the db as a PagedList*/
    val showsList = repo.getAllFavShows()

    /**Called when a user clicks on a show*/
    fun displayShowDetails(show: TvShow) {
        _navigateToSelectedShow.value = Event(show)
    }

    /**Nuke the favourites in shows*/
    fun nukeShowFavourites() {
        viewModelScope.launch { repo.nukeShowFavourites() }
    }
}