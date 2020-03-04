package com.disruption.moveery.di.landing

import androidx.lifecycle.ViewModel
import com.disruption.moveery.di.ViewModelKey
import com.disruption.moveery.ui.landing.LandingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class LandingViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    abstract fun bindsLandingViewModel(viewModel: LandingViewModel): ViewModel
}