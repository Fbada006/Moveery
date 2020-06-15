package com.droidafricana.moveery.di

import com.droidafricana.moveery.MovieApplication
import com.droidafricana.moveery.di.modules.ActivityBuildersModule
import com.droidafricana.moveery.di.modules.AppModule
import com.droidafricana.moveery.di.modules.AssistedInjectModule
import com.droidafricana.moveery.di.modules.WorkerBindingModule
import com.droidafricana.moveery.di.viewmodelfactory.ViewModelFactoryModule
import com.droidafricana.moveery.di.workerfactory.MyWorkerFactory
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