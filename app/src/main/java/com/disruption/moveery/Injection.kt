package com.disruption.moveery

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.disruption.moveery.data.MovieBoundaryCallBack
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.network.MovieApi
import com.disruption.moveery.network.MovieApiService
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope

/**Provides dependencies*/
object Injection {

    /**
     * Creates an instance of [MovieLocalCache] based on the database.
     */
    fun providesCache(context: Context): MovieLocalCache {
        val database = MovieRoomDatabase.getDatabase(context)
        return MovieLocalCache(database)
    }

/*    *//**
     * Creates an instance of [MovieRepo] based on the [MovieLocalCache] and a
     * [CoroutineScope]
     *//*
    private fun provideMovieRepository(
        context: Context,
        boundaryCallBack: MovieBoundaryCallBack,
        coroutineScope: CoroutineScope,
        movieRetrofitService: MovieApiService
    ): MovieRepo {
        val cache = providesCache(context)
        return MovieRepo(
            cache,
            provideBoundaryCallBack(context, coroutineScope, MovieApi.movieRetrofitService)
        )
    }

    private fun provideBoundaryCallBack(
        context: Context,
        coroutineScope: CoroutineScope,
        movieRetrofitService: MovieApiService
    ): MovieBoundaryCallBack {
        val cache = providesCache(context)
        return MovieBoundaryCallBack(cache, coroutineScope, movieRetrofitService)
    }

    *//**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * ViewModel objects.
     *//*
    fun provideViewModelFactory(
        context: Context
    ): ViewModelProvider.Factory {
        return ViewModelFactory(provideMovieRepository(context))
    }*/
}