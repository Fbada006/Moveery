package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.shows.TvShow

/**Expose all methods in the movie repo*/
interface IMovieRepo {

    /**Get all the movies to from the local storage*/
    fun getAllMovies(): LiveData<PagedList<Movie>>

    /**Get all the movies from the local storage*/
    fun getAllShows(): LiveData<PagedList<TvShow>>

    /**Returns the searched movie with paging involved*/
    fun getSearchedMovieList(
        queryLiveData: MutableLiveData<String>
    ): LiveData<PagedList<Movie>>

    /**Returns the searched show with paging involved*/
    fun getSearchedShowList(
        queryLiveData: MutableLiveData<String>
    ): LiveData<PagedList<TvShow>>

    /**Returns similar movies with paging to the [MovieDetailsFragment]*/
    fun getSimilarMovieList(
        movieIdLiveData: MutableLiveData<Int>
    ): LiveData<PagedList<Movie>>

    /**Returns similar shows with paging to the [ShowDetailsFragment]*/
    fun getSimilarShowsList(
        showIdLiveData: MutableLiveData<Int>
    ): LiveData<PagedList<TvShow>>
}