package com.syrous.cinemabuddy.data.usecases

import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
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
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.jvm.Throws

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
                        _flowOfPageNo.collect {
                                page ->
                            try {
                                remoteDataSource.fetchPopularMoviesList(page).collect {
                                        movieResponse -> localDataSource.saveMoviesToLocalStorage(
                                    movieResponse, POPULAR)
                                }
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                            } catch (e: HttpException) {
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                _flowOfChartedMovies.emit(Result.NetworkError(e))
                                e.printStackTrace()
                            } catch (e: Exception) {
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                _flowOfChartedMovies.emit(Result.GeneralError(e))
                                e.printStackTrace()
                            }
                        }
                    }
                    TOP_RATED -> {
                        _flowOfPageNo.collect {
                                page ->
                            try {
                                remoteDataSource.fetchTopRatedMoviesList(page).collect {
                                        movieResponse -> localDataSource.saveMoviesToLocalStorage(
                                    movieResponse, TOP_RATED)
                                }
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                            } catch (e: HttpException) {
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                _flowOfChartedMovies.emit(Result.NetworkError(e))
                                e.printStackTrace()
                            } catch (e: Exception) {
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                _flowOfChartedMovies.emit(Result.GeneralError(e))
                                e.printStackTrace()
                            }
                        }
                    }
                    UPCOMING -> {
                        _flowOfPageNo.collect {
                                page ->
                            try {
                                remoteDataSource.fetchUpcomingMoviesList(page).collect {
                                        upComingMovieResponse -> localDataSource.saveMoviesToLocalStorage(
                                    upComingMovieResponse.toMovieResponse(), UPCOMING)
                                }
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                            } catch (e: HttpException) {
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                _flowOfChartedMovies.emit(Result.NetworkError(e))
                                e.printStackTrace()
                            } catch (e: Exception) {
                                _flowOfChartedMovies.emit(Result.DoneLoading)
                                _flowOfChartedMovies.emit(Result.GeneralError(e))
                                e.printStackTrace()
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
            remoteDataSource.fetchMoviesDetails(movieId).collect {
                result -> localDataSource.saveMovieWithProductionDetails(result)
            }
        }
    }
}