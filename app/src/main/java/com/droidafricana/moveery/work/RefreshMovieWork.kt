package com.droidafricana.moveery.work

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.droidafricana.moveery.R
import com.droidafricana.moveery.data.MovieLocalCache
import com.droidafricana.moveery.di.workerfactory.ChildWorkerFactory
import com.droidafricana.moveery.network.MovieApiService
import com.droidafricana.moveery.utils.NotificationUtils
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import retrofit2.HttpException

/**Worker for the movies to refresh*/
class RefreshMovieWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val movieLocalCache: MovieLocalCache,
    private val movieRetrofitService: MovieApiService
) :
    CoroutineWorker(appContext, params) {

    /**This object has the tag for this work*/
    companion object {
        const val MOVIE_WORK_NAME = "com.disruption.moveery.RefreshMovieDataWorker"
    }

    override suspend fun doWork(): Result {
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

                //We want fresh data so the page will always be set to 1
                val result =
                    movieRetrofitService.getDiscoverMoviesAsync(
                        page = 1
                    ).await()
                movieLocalCache.refreshMoviesCache(result)

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
            Result.failure()
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}
