package com.droidafricana.moveery.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.droidafricana.moveery.models.favourites.movies.FavMovie
import com.droidafricana.moveery.models.favourites.shows.FavShow
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.movies.MovieResult
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.shows.TvShowResult
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

    /**Insert a single movie to favourites*/
    override suspend fun insertMovieToFav(movie: FavMovie) {
        movieRoomDatabase.movieDao.insertMovieToFav(movie)
    }

    /**Insert a single show to favourites*/
    override suspend fun insertShowToFav(show: FavShow) {
        movieRoomDatabase.movieDao.insertShowToFav(show)
    }

    /**Delete a movie*/
    override suspend fun deleteMovieFromFav(id: Int) {
        movieRoomDatabase.movieDao.deleteMovieFromFav(id)
    }

    /**Delete a show*/
    override suspend fun deleteShowFromFav(id: Int) {
        movieRoomDatabase.movieDao.deleteShowFromFav(id)
    }

    override suspend fun nukeMovieFavourites() {
        movieRoomDatabase.movieDao.nukeMovieFavourites()
    }

    override suspend fun nukeShowFavourites() {
        movieRoomDatabase.movieDao.nukeShowFavourites()
    }

    /**Get a movie*/
    override fun getMovieById(id: Int?): LiveData<FavMovie?> =
        movieRoomDatabase.movieDao.getFavMovieById(id)

    /**Get a show*/
    override fun getShowById(id: Int?): LiveData<FavShow?> =
        movieRoomDatabase.movieDao.getFavShowById(id)
}