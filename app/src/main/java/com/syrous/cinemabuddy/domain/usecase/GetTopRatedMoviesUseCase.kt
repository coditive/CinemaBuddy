package com.syrous.cinemabuddy.domain.usecase

import android.security.keystore.UserNotAuthenticatedException
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.model.Result.Error
import com.syrous.cinemabuddy.domain.model.Result.Success
import com.syrous.cinemabuddy.domain.repository.MovieRepository

class GetTopRatedMoviesUseCase (
    private val movieRepository: MovieRepository
) {
    sealed class UseState {
        class Success(val data: List<MovieDomainModel>): UseState()
        class Error(val e: Exception): UseState()
    }


    suspend fun execute(apiKey: String, lang: String, page: Int, region: String): UseState {

        val result = movieRepository.fetchAndCacheTopRateMovies(
            apiKey,
            lang,
            page,
            region
        )

       return when (result) {
           is Success -> {
               UseState.Success(result.data)
           }
           is Error -> {
               UseState.Error(result.exception)
           }
           else -> {
               UseState.Error(Exception("Invalid State"))
           }
       }
    }
}