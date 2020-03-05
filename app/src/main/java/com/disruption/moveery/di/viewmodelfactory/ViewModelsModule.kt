package com.disruption.moveery.di.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.disruption.moveery.di.ViewModelKey
import com.disruption.moveery.ui.landing.LandingViewModel
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

}