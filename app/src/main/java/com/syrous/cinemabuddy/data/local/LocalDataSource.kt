package com.syrous.cinemabuddy.data.local

import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

interface LocalDataSource {

    fun observeChartedMovies(chartType: ChartType, page: Int
    ): Flow<Result<List<MovieDomainModel>>>

    fun observeGenreData(): Flow<List<GenreDomainModel>>

    @Throws(Exception::class)
    suspend fun saveMoviesToLocalStorage(movieResponse: MovieResponse, chartType: ChartType)

    @Throws(Exception::class)
    suspend fun saveMovieWithProductionDetails(movieDetails: MovieDetailResponse)

    @Throws(Exception::class)
    suspend fun saveGenreList(genreResponse: GenreResponse)

}