package com.disruption.moveery

import android.content.Context
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.data.movies.MovieLocalCache

/**Provides dependencies*/
object Injection {

    /**
     * Creates an instance of [MovieLocalCache] based on the database.
     */
    fun providesCache(context: Context): MovieLocalCache {
        val database = MovieRoomDatabase.getDatabase(context)
        return MovieLocalCache(database)
    }
}