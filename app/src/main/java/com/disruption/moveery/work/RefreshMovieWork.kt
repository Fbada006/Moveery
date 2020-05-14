package com.disruption.moveery.work

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.disruption.moveery.R
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.di.workerfactory.ChildWorkerFactory
import com.disruption.moveery.network.MovieApiService
import com.disruption.moveery.utils.NotificationUtils
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import retrofit2.HttpException
import timber.log.Timber

/**Worker for the movies to refresh*/
class RefreshMovieWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val movieLocalCache: MovieLocalCache,
    private val movieRetrofitService: MovieApiService,
    private val includeAdult: Boolean
) :
    CoroutineWorker(appContext, params) {

    /**This object has the tag for this work*/
    companion object {
        const val MOVIE_WORK_NAME = "com.disruption.moveery.RefreshMovieDataWorker"
    }

    override suspend fun doWork(): Result {
        // val movieLocalCache = Injection.providesCache(applicationContext)
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
                        page = 1,
                        include_adult = includeAdult
                    ).await()
                Timber.e("Result is -------------------: ${result.movieList}")
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
            Result.retry()
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}