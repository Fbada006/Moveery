package com.disruption.moveery.di

import com.disruption.moveery.MovieApplication
import com.disruption.moveery.di.viewmodelfactory.ViewModelFactoryModule
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
        ViewModelFactoryModule::class
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
}