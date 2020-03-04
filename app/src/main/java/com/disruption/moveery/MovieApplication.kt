package com.disruption.moveery

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.work.*
import com.disruption.moveery.di.DaggerAppComponent
import com.disruption.moveery.utils.ThemeHelper
import com.disruption.moveery.work.RefreshMovieWork
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MovieApplication : DaggerApplication() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        setUpMovieRefreshWork()
        setNightMode()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build();
    }

    private fun setNightMode() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = sharedPreferences.getString(
            getString(R.string.pref_night_mode_key)
            , ThemeHelper.DEFAULT_MODE
        )
        ThemeHelper.applyTheme(themePref)
    }

    private fun setUpMovieRefreshWork() {
        applicationScope.launch {
            val constraints = Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
                setRequiresBatteryNotLow(true)
            }.build()

            val repeatingMovieRefreshRequest =
                PeriodicWorkRequestBuilder<RefreshMovieWork>(15, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshMovieWork.MOVIE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingMovieRefreshRequest
            )
        }
    }
}