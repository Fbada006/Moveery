package com.disruption.moveery.di

import com.disruption.moveery.MovieApplication
import com.disruption.moveery.di.modules.ActivityBuildersModule
import com.disruption.moveery.di.modules.AppModule
import com.disruption.moveery.di.modules.AssistedInjectModule
import com.disruption.moveery.di.modules.WorkerBindingModule
import com.disruption.moveery.di.viewmodelfactory.ViewModelFactoryModule
import com.disruption.moveery.di.workerfactory.MyWorkerFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**The main component for di*/
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class,
        AssistedInjectModule::class,
        WorkerBindingModule::class
    ]
)
interface AppComponent {

    /**Interface to handle building the [AppComponent]*/
    @Component.Builder
    interface Builder {

        /**Bind the [MovieApplication] to be supplied when needed*/
        @BindsInstance
        fun application(application: MovieApplication): Builder

        /**Build the component*/
        fun build(): AppComponent
    }

    /**Injecting the [MovieApplication]*/
    fun inject(application: MovieApplication)

    /**For creating the [MyWorkerFactory]*/
    fun factory(): MyWorkerFactory
}