package com.disruption.moveery.di

import com.disruption.moveery.ui.details.DetailsFragment
import com.disruption.moveery.ui.landing.LandingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributesLandingFragment(): LandingFragment

    @ContributesAndroidInjector
    abstract fun contributesDetailsFragment(): DetailsFragment
}