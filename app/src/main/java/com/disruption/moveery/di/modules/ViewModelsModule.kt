package com.disruption.moveery.di.modules

import androidx.lifecycle.ViewModel
import com.disruption.moveery.di.keys.ViewModelKey
import com.disruption.moveery.ui.details.movies.MovieDetailsViewModel
import com.disruption.moveery.ui.details.shows.ShowDetailsViewModel
import com.disruption.moveery.ui.favourites.movie.FavMoviesViewModel
import com.disruption.moveery.ui.favourites.shows.FavShowsViewModel
import com.disruption.moveery.ui.landing.movies.MoviesLandingViewModel
import com.disruption.moveery.ui.landing.shows.ShowsLandingViewModel
import com.disruption.moveery.ui.search.movies.MovieSearchViewModel
import com.disruption.moveery.ui.search.shows.ShowsSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**Handles creation of the different types of [ViewModel]*/
@Module
abstract class ViewModelsModule {

    /**Create the [MoviesLandingViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(MoviesLandingViewModel::class)
    abstract fun bindMoviesLandingViewModel(viewModel: MoviesLandingViewModel): ViewModel

    /**Create the [MovieSearchViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel::class)
    abstract fun bindMoviesSearchViewModel(viewModel: MovieSearchViewModel): ViewModel

    /**Create the [MovieDetailsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMoviesDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel

    /**Create the [ShowsLandingViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(ShowsLandingViewModel::class)
    abstract fun bindShowsLandingViewModel(viewModel: ShowsLandingViewModel): ViewModel

    /**Create the [ShowsSearchViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(ShowsSearchViewModel::class)
    abstract fun bindShowsSearchViewModel(viewModel: ShowsSearchViewModel): ViewModel

    /**Create the [ShowDetailsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(ShowDetailsViewModel::class)
    abstract fun bindShowsDetailsViewModel(viewModel: ShowDetailsViewModel): ViewModel

    /**Create the [FavMoviesViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(FavMoviesViewModel::class)
    abstract fun bindFavMoviesViewModel(viewModel: FavMoviesViewModel): ViewModel

    /**Create the [FavShowsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(FavShowsViewModel::class)
    abstract fun bindFavShowsViewModel(viewModel: FavShowsViewModel): ViewModel
}