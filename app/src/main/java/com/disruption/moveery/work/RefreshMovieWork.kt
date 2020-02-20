package com.disruption.moveery.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.disruption.moveery.Injection
import com.disruption.moveery.network.MovieApi
import retrofit2.HttpException

class RefreshMovieWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    /**This object has the tag for this work*/
    companion object {
        const val MOVIE_WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val movieLocalCache = Injection.providesCache(applicationContext)
        return try {
            //We want fresh data so the page will always be set to 1
            val result =
                MovieApi.movieRetrofitService.getDiscoverMoviesAsync(page = 1).await()
            movieLocalCache.refreshMoviesCache(result)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}