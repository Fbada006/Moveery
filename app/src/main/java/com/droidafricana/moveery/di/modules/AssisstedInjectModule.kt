package com.droidafricana.moveery.di.modules

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

/**This makes all of the ChildWorkerFactory implementations available in our
 * component dependency graph.*/
@Module(includes = [AssistedInject_AssistedInjectModule::class])
@AssistedModule
interface AssistedInjectModule