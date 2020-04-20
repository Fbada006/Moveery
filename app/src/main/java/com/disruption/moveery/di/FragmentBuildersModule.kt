package com.disruption.moveery.di

import com.disruption.moveery.ui.details.movies.MovieDetailsFragment
import com.disruption.moveery.ui.landing.movies.MoviesLandingFragment
import com.disruption.moveery.ui.search.movies.MovieSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**Class to handle generating injectors of the Fragments */
@Module
abstract class FragmentBuildersModule {

    /**Inject [MoviesLandingFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesLandingFragment(): MoviesLandingFragment

    /**Inject [MovieDetailsFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesDetailsFragment(): MovieDetailsFragment

    /**Inject [MovieSearchFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesSearchFragment(): MovieSearchFragment
}