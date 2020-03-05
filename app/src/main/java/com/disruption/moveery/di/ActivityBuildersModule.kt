package com.disruption.moveery.di

import com.disruption.moveery.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**Class to handle generating injectors of the Activities */
@Module(includes = [FragmentBuildersModule::class])
abstract class ActivityBuildersModule {

    /**Inject [MainActivity]*/
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}