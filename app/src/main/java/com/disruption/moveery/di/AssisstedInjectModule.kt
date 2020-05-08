package com.disruption.moveery.di

import com.disruption.moveery.AssistedInject_AssistedInjectModule
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_AssistedInjectModule::class])
@AssistedModule
interface AssistedInjectModule