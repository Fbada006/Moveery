package com.disruption.moveery.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.disruption.moveery.Injection
import com.disruption.moveery.R
import com.disruption.moveery.network.MovieApi
import com.disruption.moveery.utils.NotificationUtils
import retrofit2.HttpException

class RefreshMovieWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    val TAG = "RefreshMovieWork"

    /**This object has the tag for this work*/
    companion object {
        const val MOVIE_WORK_NAME = "RefreshMovieDataWorker"
    }

    override suspend fun doWork(): Result {
        val movieLocalCache = Injection.providesCache(applicationContext)
        return try {
            NotificationUtils.sendNotification(
                applicationContext,
                applicationContext.getString(R.string.movie_refresh_starting)
            )

            //We want fresh data so the page will always be set to 1
            val result =
                MovieApi.movieRetrofitService.getDiscoverMoviesAsync(page = 1).await()
            movieLocalCache.refreshMoviesCache(result)

            NotificationUtils.sendNotification(
                applicationContext,
                applicationContext.getString(R.string.movie_refresh_successful)
            )

            Result.success()
        } catch (e: HttpException) {
            NotificationUtils.sendNotification(
                applicationContext,
                applicationContext.getString(R.string.movie_refresh_failed)
            )
            Result.retry()
        }
    }
}