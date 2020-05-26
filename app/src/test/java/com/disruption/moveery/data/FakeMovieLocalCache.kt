package com.disruption.moveery.data

import androidx.paging.DataSource
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

    /**Insert all movies into the db*/
    override suspend fun refreshMoviesCache(movieMovieResult: MovieResult) {
        movieList?.addAll(movieMovieResult.movieList)
    }

    /**Insert all shows into the db*/
    override suspend fun refreshShowsCache(tvShowResult: TvShowResult) {
        showsList?.addAll(tvShowResult.showsList)
    }
}