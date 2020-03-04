package com.disruption.moveery.di.landing

import com.disruption.moveery.ui.landing.LandingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LandingBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            LandingViewModelModule::class
        ]
    )
    internal abstract fun contributesLandingFragment(): LandingFragment
}