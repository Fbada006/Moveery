package com.disruption.moveery.di

import com.disruption.moveery.ui.details.DetailsFragment
import com.disruption.moveery.ui.landing.LandingFragment
import com.disruption.moveery.ui.search.SearchFragment
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

    /**Inject [SearchFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesSearchFragment(): SearchFragment
}