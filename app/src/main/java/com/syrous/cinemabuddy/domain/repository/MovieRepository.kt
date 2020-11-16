package com.syrous.cinemabuddy.domain.repository

import androidx.lifecycle.LiveData
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result

interface MovieRepository {
    suspend fun fetchAndCacheGenreList(apiKey: String, lang: String)
    suspend fun getMoviesFromLocalStorage()
    fun observeGenreData(lang: String): LiveData<List<GenreDomainModel>>
    suspend fun fetchAndCacheTopRateMovies(apiKey: String, lang: String, page: Int, region: String?): Result<Boolean>
    suspend fun fetchAndCachePopularMovies(apiKey: String, lang: String, page: Int, region: String?): Result<Boolean>
}