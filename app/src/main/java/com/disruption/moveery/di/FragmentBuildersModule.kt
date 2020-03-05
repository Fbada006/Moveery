package com.disruption.moveery.di

import com.disruption.moveery.ui.details.DetailsFragment
import com.disruption.moveery.ui.landing.LandingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**Class to handle generating injectors of the Fragments */
@Module
abstract class FragmentBuildersModule {

    /**Inject [LandingFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesLandingFragment(): LandingFragment

    /**Inject [DetailsFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesDetailsFragment(): DetailsFragment
}