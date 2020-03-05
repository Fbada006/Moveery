package com.disruption.moveery.di

import com.disruption.moveery.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentBuildersModule::class])
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}