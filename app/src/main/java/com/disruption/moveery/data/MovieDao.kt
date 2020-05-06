package com.disruption.moveery.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.disruption.moveery.models.movies.movie.Movie
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

//
//    /**Empty the database if we are getting new data*/
//    @Query("DELETE FROM movies")
//    suspend fun clearMoviesTable()
}