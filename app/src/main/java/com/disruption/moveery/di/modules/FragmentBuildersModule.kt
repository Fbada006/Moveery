package com.disruption.moveery.di.modules

import com.disruption.moveery.ui.details.movies.MovieDetailsFragment
import com.disruption.moveery.ui.details.shows.ShowDetailsFragment
import com.disruption.moveery.ui.landing.movies.MoviesLandingFragment
import com.disruption.moveery.ui.landing.shows.ShowsLandingFragment
import com.disruption.moveery.ui.search.movies.MovieSearchFragment
import com.disruption.moveery.ui.search.shows.ShowsSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**Class to handle generating injectors of the Fragments */
@Module
abstract class FragmentBuildersModule {

    /**Inject [MoviesLandingFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesMoviesLandingFragment(): MoviesLandingFragment

    /**Inject [MovieDetailsFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesMovieDetailsFragment(): MovieDetailsFragment

    /**Inject [MovieSearchFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesMovieSearchFragment(): MovieSearchFragment

    /**Inject [ShowsLandingFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesShowsLandingFragment(): ShowsLandingFragment

    /**Inject [ShowDetailsFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesShowDetailsFragment(): ShowDetailsFragment

    /**Inject [ShowsSearchFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesShowsSearchFragment(): ShowsSearchFragment
}