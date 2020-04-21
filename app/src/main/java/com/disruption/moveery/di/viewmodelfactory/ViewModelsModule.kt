package com.disruption.moveery.di.viewmodelfactory

import androidx.lifecycle.ViewModel
import com.disruption.moveery.di.ViewModelKey
import com.disruption.moveery.ui.details.movies.MovieDetailsViewModel
import com.disruption.moveery.ui.landing.movies.MoviesLandingViewModel
import com.disruption.moveery.ui.search.movies.MovieSearchViewModel
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
    abstract fun bindLandingViewModel(viewModel: MoviesLandingViewModel): ViewModel

    /**Create the [MovieSearchViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: MovieSearchViewModel): ViewModel

    /**Create the [MovieDetailsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}