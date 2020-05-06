package com.disruption.moveery

import android.app.Activity
import android.app.Application
import androidx.preference.PreferenceManager
import androidx.work.*
import com.disruption.moveery.di.AppInjector
import com.disruption.moveery.utils.ThemeHelper
import com.disruption.moveery.work.RefreshMovieWork
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**The Application class*/
class MovieApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        setUpMovieRefreshWork()
        setNightMode()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
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
                PeriodicWorkRequestBuilder<RefreshMovieWork>(1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshMovieWork.MOVIE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingMovieRefreshRequest
            )
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}