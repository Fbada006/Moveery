package com.disruption.moveery.di

import com.disruption.moveery.di.keys.WorkerKey
import com.disruption.moveery.di.workerfactory.ChildWorkerFactory
import com.disruption.moveery.work.RefreshMovieWork
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**Handles creation of the different types of [Worker]*/
@Module
interface WorkerBindingModule {

    /**Create the [RefreshMovieWork]*/
    @Binds
    @IntoMap
    @WorkerKey(RefreshMovieWork::class)
    fun bindHelloWorldWorker(factory: RefreshMovieWork.Factory): ChildWorkerFactory
}