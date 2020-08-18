package com.droidafricana.moveery.di.modules

import androidx.lifecycle.ViewModel
import com.droidafricana.moveery.di.keys.ViewModelKey
import com.droidafricana.moveery.ui.details.movies.MovieDetailsViewModel
import com.droidafricana.moveery.ui.details.shows.ShowDetailsViewModel
import com.droidafricana.moveery.ui.details.similardetails.similarmovie.SimilarMovieDetailsViewModel
import com.droidafricana.moveery.ui.details.similardetails.similarshow.SimilarShowsViewModel
import com.droidafricana.moveery.ui.favourites.movie.FavMoviesViewModel
import com.droidafricana.moveery.ui.favourites.shows.FavShowsViewModel
import com.droidafricana.moveery.ui.landing.movies.MoviesLandingViewModel
import com.droidafricana.moveery.ui.landing.shows.ShowsLandingViewModel
import com.droidafricana.moveery.ui.search.movies.MovieSearchViewModel
import com.droidafricana.moveery.ui.search.shows.ShowsSearchViewModel
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

    /**Create the [SimilarMovieDetailsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(SimilarMovieDetailsViewModel::class)
    abstract fun bindSimilarMoviesViewModel(viewModel: SimilarMovieDetailsViewModel): ViewModel

    /**Create the [SimilarShowsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(SimilarShowsViewModel::class)
    abstract fun bindSimilarShowsViewModel(viewModel: SimilarShowsViewModel): ViewModel
}