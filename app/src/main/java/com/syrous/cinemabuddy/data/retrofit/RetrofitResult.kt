package com.syrous.cinemabuddy.data.retrofit

import com.syrous.cinemabuddy.domain.model.Result
import retrofit2.HttpException

sealed class RetrofitResult<out T> {
    data class Success<out R>(val data: R): RetrofitResult<R>()
    data class NetworkError(val exception: HttpException): RetrofitResult<Nothing>()
}