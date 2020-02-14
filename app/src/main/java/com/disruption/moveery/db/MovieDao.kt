package com.disruption.moveery.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.disruption.moveery.models.Movie

/**The [Dao] that offers access to the db*/
@Dao
interface MovieDao {

    /**Returns all the movies in the DB*/
    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    /**Uses coroutines to insert the data into the db on a different thread*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(vararg movies: Movie)
}