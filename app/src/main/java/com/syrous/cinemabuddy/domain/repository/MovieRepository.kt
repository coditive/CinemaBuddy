package com.syrous.cinemabuddy.domain.repository

import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result

interface MovieRepository {
    suspend fun fetchAndCacheGenreList(apiKey: String, lang: String): Result<Boolean>
    suspend fun getMoviesFromLocalStorage()
    suspend fun fetchAndCacheTopRateMovies(apiKey: String, lang: String, page: Int, region: String?): Result<Boolean>
    suspend fun fetchAndCachePopularMovies(apiKey: String, lang: String, page: Int, region: String?): Result<Boolean>
}