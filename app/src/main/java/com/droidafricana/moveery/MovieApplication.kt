package com.droidafricana.moveery

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.droidafricana.moveery.di.AppInjector
import com.droidafricana.moveery.di.DaggerAppComponent
import com.droidafricana.moveery.utils.ThemeHelper
import com.droidafricana.moveery.work.RefreshMovieWork
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
open class MovieApplication : Application(), HasActivityInjector
//, Configuration.Provider
{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
        initWorkManagerWithDagger()

        setUpMovieRefreshWork()
        setNightMode()
        initTimber()
    }

    private fun initWorkManagerWithDagger() {
        val factory = DaggerAppComponent.builder()
            .application(this)
            .build()
            .factory()

        WorkManager.initialize(
            this, Configuration.Builder()
                .setWorkerFactory(factory)
                .build()
        )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun setNightMode() {
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
                PeriodicWorkRequestBuilder<RefreshMovieWork>(
                    1, TimeUnit.DAYS,
                    3, TimeUnit.HOURS
                )
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                RefreshMovieWork.MOVIE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingMovieRefreshRequest
            )
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

//    override fun getWorkManagerConfiguration(): Configuration =
//        Configuration.Builder().build()
}
