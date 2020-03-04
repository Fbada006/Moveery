package com.disruption.moveery.di

import android.content.Context
import com.disruption.moveery.MovieApplication
import com.disruption.moveery.di.details.DetailsBuildersModule
import com.disruption.moveery.di.landing.LandingBuildersModule
import com.disruption.moveery.di.viewmodelfactory.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        LandingBuildersModule::class,
        DetailsBuildersModule::class
    ]
)
interface AppComponent : AndroidInjector<MovieApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}