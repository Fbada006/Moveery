package com.disruption.moveery.di.workerfactory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

/**Every time a new worker is requested, [WorkManager] will request it from this class*/
class MyWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry =
            workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }

        return try {
            val factoryProvider = foundEntry?.value
            // ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
            factoryProvider?.get()?.create(appContext, workerParameters)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}

/**A factory for each Worker that supports constructor injection*/
interface ChildWorkerFactory {
    /**Method to actually create the worker*/
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}