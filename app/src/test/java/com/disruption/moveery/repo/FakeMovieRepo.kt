package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.shows.TvShow

/**Fake class for the repo
 * TODO
 * */
class FakeMovieRepo : IMovieRepo {

    private val observableMovies = MutableLiveData<PagedList<Movie>>()
    private val observableShows = MutableLiveData<PagedList<Short>>()

    /**Get all movies*/
    override fun getAllMovies(): LiveData<PagedList<Movie>> {
        TODO("Not yet implemented")
    }

    /**Get all shows*/
    override fun getAllShows(): LiveData<PagedList<TvShow>> {
        TODO("Not yet implemented")
    }

    /**Get searched movies*/
    override fun getSearchedMovieList(queryLiveData: MutableLiveData<String>): LiveData<PagedList<Movie>> {
        TODO("Not yet implemented")
    }

    /**Get searched shows*/
    override fun getSearchedShowList(queryLiveData: MutableLiveData<String>): LiveData<PagedList<TvShow>> {
        TODO("Not yet implemented")
    }


    override fun getSimilarMovieList(movieIdLiveData: MutableLiveData<Int>): LiveData<PagedList<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarShowsList(showIdLiveData: MutableLiveData<Int>): LiveData<PagedList<TvShow>> {
        TODO("Not yet implemented")
    }
}