package com.disruption.moveery.di.viewmodelfactory

import androidx.lifecycle.ViewModel
import com.disruption.moveery.di.ViewModelKey
import com.disruption.moveery.ui.details.DetailsViewModel
import com.disruption.moveery.ui.landing.LandingViewModel
import com.disruption.moveery.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**Handles creation of the different types of [ViewModel]*/
@Module
abstract class ViewModelsModule {

    /**Create the [LandingViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    abstract fun bindLandingViewModel(viewModel: LandingViewModel): ViewModel

    /**Create the [SearchViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    /**Create the [DetailsViewModel]*/
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
}