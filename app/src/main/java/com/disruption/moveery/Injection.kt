package com.disruption.moveery

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.repo.MovieLocalCache
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope

/**Provides dependencies*/
object Injection {

    /**
     * Creates an instance of [MovieLocalCache] based on the database.
     */
    private fun providesCache(context: Context): MovieLocalCache {
        val database = MovieRoomDatabase.getDatabase(context)
        return MovieLocalCache(database)
    }

    /**
     * Creates an instance of [MovieRepo] based on the [MovieLocalCache] and a
     * [CoroutineScope]
     */
    private fun provideMovieRepository(
        context: Context,
        coroutineScope: CoroutineScope
    ): MovieRepo {
        return MovieRepo(providesCache(context), coroutineScope)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * ViewModel objects.
     */
    fun provideViewModelFactory(
        context: Context,
        coroutineScope: CoroutineScope
    ): ViewModelProvider.Factory {
        return ViewModelFactory(provideMovieRepository(context, coroutineScope))
    }
}