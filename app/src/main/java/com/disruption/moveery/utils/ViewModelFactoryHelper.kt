package com.disruption.moveery.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.disruption.moveery.repo.MovieRepo
import com.disruption.moveery.ui.landing.LandingViewModel

///**
// * Convenience factory to handle ViewModels with one parameter.
// * @param constructor A function (A) -> T that returns an instance of the ViewModel (typically pass
// * the constructor)
// * @return a function of one argument that returns ViewModelProvider.Factory for ViewModelProviders
// */
//fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
//            (A) -> ViewModelProvider.NewInstanceFactory {
//    return { arg: A ->
//        object : ViewModelProvider.NewInstanceFactory() {
//            @Suppress("UNCHECKED_CAST")
//            override fun <V : ViewModel> create(modelClass: Class<V>): V {
//                return constructor(arg) as V
//            }
//        }
//    }
//}

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: MovieRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LandingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LandingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
