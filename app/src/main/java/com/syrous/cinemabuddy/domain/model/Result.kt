package com.syrous.cinemabuddy.domain.model

import retrofit2.HttpException

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {
    object NotInitialized: Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class NetworkError(val exception: HttpException): Result<Nothing>()
    data class GeneralError(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object DoneLoading: Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is GeneralError -> "Error[exception=$exception]"
            Loading -> "Loading"
            NotInitialized -> "NotInitialized"
            DoneLoading -> "DoneLoading"
            is NetworkError -> "Network Error[$exception]"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null
