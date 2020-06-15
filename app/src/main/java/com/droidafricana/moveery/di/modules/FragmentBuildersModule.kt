package com.droidafricana.moveery.di.modules

import com.droidafricana.moveery.ui.details.movies.MovieDetailsFragment
import com.droidafricana.moveery.ui.details.shows.ShowDetailsFragment
import com.droidafricana.moveery.ui.favourites.movie.FavouriteMoviesFragment
import com.droidafricana.moveery.ui.favourites.shows.FavouriteShowsFragment
import com.droidafricana.moveery.ui.landing.movies.MoviesLandingFragment
import com.droidafricana.moveery.ui.landing.shows.ShowsLandingFragment
import com.droidafricana.moveery.ui.search.movies.MovieSearchFragment
import com.droidafricana.moveery.ui.search.shows.ShowsSearchFragment
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

    /**Inject [FavouriteMoviesFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesFavMoviesFragment(): FavouriteMoviesFragment

    /**Inject [FavouriteShowsFragment]*/
    @ContributesAndroidInjector
    abstract fun contributesFavShowsFragment(): FavouriteShowsFragment
}