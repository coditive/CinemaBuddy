package com.syrous.cinemabuddy.domain.usecase

import com.syrous.cinemabuddy.data.local.ChartedMoviesDao
import com.syrous.cinemabuddy.domain.model.Result.Error
import com.syrous.cinemabuddy.domain.model.Result.Success
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase constructor (
    private val movieRepository: MovieRepository,
    private val chartedMoviesDao: ChartedMoviesDao
) {

    val topRatedMoviesLiveData = chartedMoviesDao

    sealed class UseState {
        class Success(val data: Boolean): UseState()
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
               UseState.Success(true)
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