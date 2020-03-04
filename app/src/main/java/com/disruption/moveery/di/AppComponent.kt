package com.disruption.moveery.di

import android.app.Application
import com.disruption.moveery.MovieApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<MovieApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application?): Builder

        fun build(): AppComponent
    }
}