package com.droidafricana.moveery.di.modules

import com.droidafricana.moveery.di.keys.WorkerKey
import com.droidafricana.moveery.di.workerfactory.ChildWorkerFactory
import com.droidafricana.moveery.work.RefreshMovieWork
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