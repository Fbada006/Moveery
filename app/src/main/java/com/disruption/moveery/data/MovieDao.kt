package com.disruption.moveery.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.disruption.moveery.models.movie.Movie

/**The [Dao] that offers access to the db*/
@Dao
interface MovieDao {

    /**Returns all the movies in the DB*/
    @Query("SELECT * FROM movies")
    fun getAllMovies(): DataSource.Factory<Int, Movie>

    /**Uses coroutines to insert the data into the db on a different thread*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<Movie>)
//
//    /**Empty the database if we are getting new generalArticles*/
//    @Query("DELETE FROM movies")
//    suspend fun clearMoviesTable()
}