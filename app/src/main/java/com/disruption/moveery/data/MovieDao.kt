package com.disruption.moveery.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.disruption.moveery.models.favourites.movies.FavMovie
import com.disruption.moveery.models.favourites.shows.FavShow
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.shows.TvShow

/**The [Dao] that offers access to the db*/
@Dao
interface MovieDao {

    /**Returns all the movies in the DB*/
    @Query("SELECT * FROM movies")
    fun getAllMovies(): DataSource.Factory<Int, Movie>

    /**Uses coroutines to insert the data into the db on a different thread*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<Movie>)

    /**Returns all the shows in the DB*/
    @Query("SELECT * FROM shows")
    fun getAllShows(): DataSource.Factory<Int, TvShow>

    /**Uses coroutines to insert the data into the db on a different thread*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllShows(shows: List<TvShow>)

    /**Get all the fav movies*/
    @Query("SELECT * FROM favMovies")
    fun getAllFavMovies(): DataSource.Factory<Int, FavMovie>

    /**Get all the fav shows*/
    @Query("SELECT * FROM favShows")
    fun getAllFavShows(): DataSource.Factory<Int, FavShow>

    /**Insert favourite movie into table*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToFav(movie: FavMovie)

    /**Insert favourite show into table*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowToFav(show: FavShow)

    /**Get movie based on its id*/
    @Query("SELECT * FROM favMovies WHERE id = :id")
    fun getFavMovieById(id: Int?): LiveData<FavMovie?>

    /**Get show based on its id*/
    @Query("SELECT * FROM favShows WHERE id = :id")
    fun getFavShowById(id: Int): LiveData<FavShow>

    /**Delete a movie from fav*/
    @Query("DELETE FROM favMovies WHERE id = :id")
    suspend fun deleteMovieFromFav(id: Int)

    /**Delete a show from fav*/
    @Query("DELETE FROM favShows WHERE id = :id")
    suspend fun deleteShowFromFav(id: Int)
}