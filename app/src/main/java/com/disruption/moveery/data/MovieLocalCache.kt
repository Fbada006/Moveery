package com.disruption.moveery.data

import androidx.paging.DataSource
import com.disruption.moveery.models.favourites.movies.FavMovie
import com.disruption.moveery.models.favourites.shows.FavShow
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.movies.MovieResult
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.models.shows.TvShowResult
import javax.inject.Inject

/**This class gets the data from the API and saves it to the offline db*/
class MovieLocalCache @Inject constructor(private val movieRoomDatabase: MovieRoomDatabase) :
    IMovieLocalCache {

    /**Room executes all queries on a separate thread.*/
    override fun getMovieData(): DataSource.Factory<Int, Movie> =
        movieRoomDatabase.movieDao.getAllMovies()

    /**Get all the shows*/
    override fun getShowsData(): DataSource.Factory<Int, TvShow> =
        movieRoomDatabase.movieDao.getAllShows()

    /**Get all fav movies*/
    override fun getFavMovieData(): DataSource.Factory<Int, FavMovie> =
        movieRoomDatabase.movieDao.getAllFavMovies()

    /**Get all fav shows*/
    override fun getFavShowsData(): DataSource.Factory<Int, FavShow> =
        movieRoomDatabase.movieDao.getAllFavShows()

    /**Insert the movies into the database on a background thread*/
    override suspend fun refreshMoviesCache(movieMovieResult: MovieResult) {
        movieRoomDatabase.movieDao.insertAllMovies(movieMovieResult.movieList)
    }

    /**Insert TV shows*/
    override suspend fun refreshShowsCache(tvShowResult: TvShowResult) {
        movieRoomDatabase.movieDao.insertAllShows(tvShowResult.showsList)
    }
}