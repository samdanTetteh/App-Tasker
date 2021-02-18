package com.ijikod.apptasker.data

import java.lang.Exception

/**
 * A generic class that holds a value with its loading status
 * @param <T>
 *
 */

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    object Loading: Result<Nothing>()
    data class Error(val exception: Exception): Result<Nothing>()

}

/**
 * Extention funtion to check if request is successful
 */
val Result<*>.isSuccessful
    get() = this is Result.Success && data != null