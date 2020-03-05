package com.disruption.moveery.work

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.disruption.moveery.Injection
import com.disruption.moveery.R
import com.disruption.moveery.network.MovieApi
import com.disruption.moveery.utils.NotificationUtils
import retrofit2.HttpException

/**Worker for the movies to refresh*/
class RefreshMovieWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    /**This object has the tag for this work*/
    companion object {
        const val MOVIE_WORK_NAME = "RefreshMovieDataWorker"
    }

    override suspend fun doWork(): Result {
        val movieLocalCache = Injection.providesCache(applicationContext)
        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val areNotificationsEnabled = sp.getBoolean(
            applicationContext.getString(R.string.pref_show_notifications_key),
            applicationContext.resources.getBoolean(R.bool.pref_show_notifications_default)
        )

        return try {
            if (areNotificationsEnabled) {
                NotificationUtils.sendNotification(
                    applicationContext,
                    applicationContext.getString(R.string.movie_refresh_starting)
                )
            }

            //We want fresh data so the page will always be set to 1
            val result =
                MovieApi.movieRetrofitService.getDiscoverMoviesAsync(page = 1).await()
            movieLocalCache.refreshMoviesCache(result)

            if (areNotificationsEnabled) {
                NotificationUtils.sendNotification(
                    applicationContext,
                    applicationContext.getString(R.string.movie_refresh_successful)
                )
            }

            Result.success()
        } catch (e: HttpException) {
            if (areNotificationsEnabled) {
                NotificationUtils.sendNotification(
                    applicationContext,
                    applicationContext.getString(R.string.movie_refresh_failed)
                )
            }
            Result.retry()
        }
    }
}