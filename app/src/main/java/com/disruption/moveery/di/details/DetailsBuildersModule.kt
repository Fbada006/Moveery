package com.disruption.moveery.di.details

import com.disruption.moveery.ui.details.DetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailsBuildersModule {
    @ContributesAndroidInjector
    internal abstract fun contributesDetailsFragment(): DetailsFragment

}