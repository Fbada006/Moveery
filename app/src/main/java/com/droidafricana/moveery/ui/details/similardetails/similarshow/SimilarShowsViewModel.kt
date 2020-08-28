package com.droidafricana.moveery.ui.details.similardetails.similarshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.repo.MovieRepo
import com.droidafricana.moveery.utils.Constants
import com.droidafricana.moveery.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class SimilarShowsViewModel @Inject constructor(private val repo: MovieRepo) :
    ViewModel() {

    private val _videoRes = MutableLiveData<Resource<List<Video>>>()

    /**The resource that will supply the videos*/
    val videoRes: LiveData<Resource<List<Video>>>
        get() = _videoRes

    /**Method that gets the videos*/
    fun getVideosResource(id: Int) {
        viewModelScope.launch {
            _videoRes.value = repo.getVideos(Constants.SHOW_TYPE, id).value
        }
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
