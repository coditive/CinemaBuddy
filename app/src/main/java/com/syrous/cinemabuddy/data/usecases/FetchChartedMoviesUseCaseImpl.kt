package com.syrous.cinemabuddy.data.usecases

import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.RetrofitResult
import com.syrous.cinemabuddy.data.retrofit.response.toMovieResponse
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.ChartType.*
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.usecases.FetchChartedMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
class FetchChartedMoviesUseCaseImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): FetchChartedMoviesUseCase {

    private val _flowOfChartedMovies =
        MutableStateFlow<Result<List<MovieDomainModel>>>(Result.NotInitialized)

    private val _flowOfChartType = MutableStateFlow(TOP_RATED)

    private val _flowOfPageNo = MutableStateFlow(1)

    override val flowOfChartedMovies: StateFlow<Result<List<MovieDomainModel>>> = _flowOfChartedMovies

    @Throws(Exception::class)
    override suspend fun execute() {
        _flowOfChartedMovies.emit(Result.Loading)
        withContext(Dispatchers.IO) {
            _flowOfChartType.collect {
                when(it){
                    NORMAL -> throw RuntimeException("Invalid Movie Type.")
                    POPULAR -> {
                        _flowOfPageNo.collect { page ->
                            remoteDataSource.fetchPopularMoviesList(page).take(1)
                                .collect { movieResponse ->
                                    _flowOfChartedMovies.emit(Result.DoneLoading)
                                    if (movieResponse is RetrofitResult.Success) {
                                        localDataSource.saveMoviesToLocalStorage(
                                            movieResponse.data, POPULAR
                                        )
                                    } else if (movieResponse is RetrofitResult.NetworkError) {
                                        _flowOfChartedMovies.emit(Result.NetworkError(movieResponse.exception))
                                    }
                                }
                        }
                    }
                    TOP_RATED -> {
                        _flowOfPageNo.collect {
                                page ->
                            remoteDataSource.fetchTopRatedMoviesList(page).take(1).collect {
                                    movieResponse ->
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                if(movieResponse is RetrofitResult.Success) {
                                    localDataSource.saveMoviesToLocalStorage(
                                        movieResponse.data, TOP_RATED)
                                } else if(movieResponse is RetrofitResult.NetworkError) {
                                    _flowOfChartedMovies.emit(Result.NetworkError(movieResponse.exception))
                                }
                            }

                        }
                    }
                    UPCOMING -> {
                        _flowOfPageNo.collect {
                                page ->
                            remoteDataSource.fetchUpcomingMoviesList(page).take(1).collect { upComingMovieResponse ->
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                if (upComingMovieResponse is RetrofitResult.Success) {
                                    localDataSource.saveMoviesToLocalStorage(
                                        upComingMovieResponse.data.toMovieResponse(), UPCOMING
                                    )
                                } else if (upComingMovieResponse is RetrofitResult.NetworkError) {
                                    _flowOfChartedMovies.emit(
                                        Result.NetworkError(
                                            upComingMovieResponse.exception
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override suspend fun loadChartedMoviesFromLocalStorage() {
        _flowOfChartedMovies.emit(Result.Loading)
        _flowOfChartType.combine(_flowOfPageNo) {
                chartTypeFlow, pageFlow ->
            localDataSource.observeChartedMovies(chartTypeFlow, pageFlow)
        }.flattenConcat().collect {
            _flowOfChartedMovies.emit(Result.DoneLoading)
            _flowOfChartedMovies.emit(it)
        }
    }



    override suspend fun reload() {
        TODO("Not yet implemented")
    }

    override suspend fun loadNextChartedMoviesPage(page: Int) {
        _flowOfPageNo.emit(page)
    }

    override suspend fun setChartType(chartType: ChartType) {
        _flowOfChartType.value = chartType
    }

    override fun getChartType(): ChartType {
        TODO("Not yet implemented")
    }


    private suspend fun fetchMovieDetails(movieId: Int) {
        withContext(Dispatchers.IO) {
            remoteDataSource.fetchMoviesDetails(movieId).take(1).collect {
                    result -> if(result is RetrofitResult.Success) {
                localDataSource.saveMovieWithProductionDetails(result.data)
            }
            }
        }
    }
}