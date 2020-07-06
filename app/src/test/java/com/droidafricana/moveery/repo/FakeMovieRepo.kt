package com.droidafricana.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.droidafricana.moveery.models.movies.LandingMovieResource
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.shows.LandingShowResource
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.utils.Resource

/**Fake class for the repo
 * TODO
 * */
class FakeMovieRepo : IMovieRepo {

    private val observableMovies = MutableLiveData<PagedList<Movie>>()
    private val observableShows = MutableLiveData<PagedList<Short>>()

    override fun getLandingMovies(): LandingMovieResource {
        TODO("Not yet implemented")
    }

    override fun getLandingShows(): LandingShowResource {
        TODO("Not yet implemented")
    }

    override fun getAllFavMovies(): LiveData<PagedList<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getAllFavShows(): LiveData<PagedList<TvShow>> {
        TODO("Not yet implemented")
    }

    override fun getSearchedMovieList(queryLiveData: MutableLiveData<String>): LiveData<PagedList<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getSearchedShowList(queryLiveData: MutableLiveData<String>): LiveData<PagedList<TvShow>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarMovieList(movieIdLiveData: MutableLiveData<Int>): LiveData<PagedList<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarShowsList(showIdLiveData: MutableLiveData<Int>): LiveData<PagedList<TvShow>> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideos(type: String, id: Int): LiveData<Resource<List<Video>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieToFav(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun insertShowToFav(show: TvShow) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovieFromFav(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShowFromFav(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun nukeMovieFavourites() {
        TODO("Not yet implemented")
    }

    override suspend fun nukeShowFavourites() {
        TODO("Not yet implemented")
    }

    override fun getMovieById(id: Int?): LiveData<Movie?> {
        TODO("Not yet implemented")
    }

    override fun getShowById(id: Int?): LiveData<TvShow?> {
        TODO("Not yet implemented")
    }


}