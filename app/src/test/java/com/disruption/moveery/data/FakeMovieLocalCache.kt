package com.disruption.moveery.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.disruption.moveery.models.favourites.movies.FavMovie
import com.disruption.moveery.models.favourites.shows.FavShow
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.movies.MovieResult
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.models.shows.TvShowResult

/**The fake movie cache*/
class FakeMovieLocalCache(
    var movies: DataSource.Factory<Int, Movie>? = null,
    var shows: DataSource.Factory<Int, TvShow>? = null,
    var movieList: MutableList<Movie>? = mutableListOf(),
    var showsList: MutableList<TvShow>? = mutableListOf()
) : IMovieLocalCache {

    /**Get all fake movies*/
    override fun getMovieData(): DataSource.Factory<Int, Movie> {
        return movies!!
    }

    /**Get all fake shows*/
    override fun getShowsData(): DataSource.Factory<Int, TvShow> {
        return shows!!
    }

    override fun getFavMovieData(): DataSource.Factory<Int, FavMovie> {
        TODO("Not yet implemented")
    }

    override fun getFavShowsData(): DataSource.Factory<Int, FavShow> {
        TODO("Not yet implemented")
    }

    /**Insert all movies into the db*/
    override suspend fun refreshMoviesCache(movieMovieResult: MovieResult) {
        movieList?.addAll(movieMovieResult.movieList)
    }

    /**Insert all shows into the db*/
    override suspend fun refreshShowsCache(tvShowResult: TvShowResult) {
        showsList?.addAll(tvShowResult.showsList)
    }

    override suspend fun insertMovieToFav(movie: FavMovie) {
        TODO("Not yet implemented")
    }

    override suspend fun insertShowToFav(show: FavShow) {
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

    override fun getMovieById(id: Int?): LiveData<FavMovie?> {
        TODO("Not yet implemented")
    }

    override fun getShowById(id: Int?): LiveData<FavShow?> {
        TODO("Not yet implemented")
    }
}