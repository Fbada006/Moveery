package com.disruption.moveery.di

import com.disruption.moveery.MovieApplication
import com.disruption.moveery.di.viewmodelfactory.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

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

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MovieApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: MovieApplication)
}