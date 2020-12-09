package com.syrous.cinemabuddy.domain.usecases

import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface FetchChartedMoviesUseCase: UseCase {

    val flowOfChartedMovies: Flow<Result<List<MovieDomainModel>>>

    suspend fun loadChartedMoviesFromLocalStorage()

    suspend fun reload()

    suspend fun loadNextChartedMoviesPage(page: Int)

    suspend fun setChartType(chartType: ChartType)

    fun getChartType(): ChartType
}