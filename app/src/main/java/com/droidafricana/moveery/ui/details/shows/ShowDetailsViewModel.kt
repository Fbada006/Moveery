package com.droidafricana.moveery.ui.details.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Constants.SHOW_TYPE
import com.droidafricana.moveery.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**The ViewModel that handles displaying data on the [ShowDetailsFragment]*/
class ShowDetailsViewModel @Inject constructor(private val repo: MovieRepo) : ViewModel() {

    private val showIdLiveData = MutableLiveData<Int>()

    /**The list of similar shows*/
    val showList: LiveData<PagedList<TvShow>>

    private val _videoRes = MutableLiveData<Resource<List<Video>>>()

    /**The resource that will supply the videos*/
    val videoRes: LiveData<Resource<List<Video>>>
        get() = _videoRes

    init {
        showList = repo.getSimilarShowsList(showIdLiveData)
    }

    /**Method that gets the videos*/
    fun getVideosResource(id: Int) {
        viewModelScope.launch {
            _videoRes.value = repo.getVideos(SHOW_TYPE, id).value
        }
    }

    /**
     * Supply the show ID to show the similar shows
     */
    fun getSimilarShows(showId: Int) {
        showIdLiveData.postValue(showId)
    }

    /**Insert a show to favourites*/
    fun insertShowIntoFav(show: TvShow) {
        viewModelScope.launch { repo.insertShowToFav(show) }
    }

    /**Delete a show from favourites*/
    fun deleteShowFromFav(id: Int) {
        viewModelScope.launch { repo.deleteShowFromFav(id) }
    }

    /**If the show is null, it is not in fav and so the like button should be in the un-liked state
     * and vice-versa
     */
    fun isShowInFav(id: Int) = repo.getShowById(id)
}
