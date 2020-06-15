package com.droidafricana.moveery.di.modules

import com.droidafricana.moveery.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**Class to handle generating injectors of the Activities */
@Module(includes = [FragmentBuildersModule::class])
abstract class ActivityBuildersModule {

    /**Inject [MainActivity]*/
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}