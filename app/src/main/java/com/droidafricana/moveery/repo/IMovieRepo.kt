package com.droidafricana.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.droidafricana.moveery.models.movies.LandingMovieResult
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.utils.Resource

/**Expose all methods in the movie repo*/
interface IMovieRepo {

    /**Get all the movies to from the local storage*/
    fun getLandingMovies(): LandingMovieResult

    /**Get all the movies from the local storage*/
    fun getAllShows(): LiveData<PagedList<TvShow>>

    /**Get all the fav movies*/
    fun getAllFavMovies(): LiveData<PagedList<Movie>>

    /**Get all the fav shows*/
    fun getAllFavShows(): LiveData<PagedList<TvShow>>

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

    /**Gets the trailers for either the show or the movie*/
    suspend fun getVideos(type: String, id: Int): LiveData<Resource<List<Video>>>

    /**Insert a single movie to favourites*/
    suspend fun insertMovieToFav(movie: Movie)

    /**Insert a show to favourites*/
    suspend fun insertShowToFav(show: TvShow)

    /**Delete a single movie from favourites*/
    suspend fun deleteMovieFromFav(id: Int)

    /**Delete a single show from favourites*/
    suspend fun deleteShowFromFav(id: Int)

    /**Delete all movies from fav*/
    suspend fun nukeMovieFavourites()

    /**Delete all shows from fav*/
    suspend fun nukeShowFavourites()

    /**Get a movie based on its id*/
    fun getMovieById(id: Int?): LiveData<Movie?>

    /**Get a show based on its id*/
    fun getShowById(id: Int?): LiveData<TvShow?>
}