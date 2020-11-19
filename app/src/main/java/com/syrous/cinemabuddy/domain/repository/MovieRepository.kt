package com.syrous.cinemabuddy.domain.repository

import androidx.lifecycle.LiveData
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchAndCacheGenreList(apiKey: String, lang: String)
    fun observeChartedMovies(chartType: ChartType): LiveData<List<MovieDomainModel>>
    fun observeGenreData(lang: String): Flow<List<GenreDomainModel>>
    suspend fun fetchAndCacheTopRateMovies(apiKey: String, lang: String, page: Int, region: String?)
    suspend fun fetchAndCachePopularMovies(apiKey: String, lang: String, page: Int, region: String?): Result<Boolean>
}