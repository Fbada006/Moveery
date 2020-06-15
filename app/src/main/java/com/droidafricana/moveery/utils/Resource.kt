package com.droidafricana.moveery.utils

import com.droidafricana.moveery.utils.Resource.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param T is the data from the API call
 * @param status is the current status of the call
 * @param message is the error message (if any)
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        /**Success*/
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        /**Error*/
        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        /**Still loading data*/
        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }

    /**
     * Status of a resource that is provided to the UI.
     */
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}