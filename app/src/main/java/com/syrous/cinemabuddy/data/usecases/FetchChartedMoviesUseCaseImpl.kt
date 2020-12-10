package com.syrous.cinemabuddy.data.usecases

import android.util.Log
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.local.model.toMovieDomainModel
import com.syrous.cinemabuddy.data.retrofit.model.toChartedMovie
import com.syrous.cinemabuddy.data.retrofit.model.toMovieDbModel
import com.syrous.cinemabuddy.data.retrofit.model.toMovieWithGenre
import com.syrous.cinemabuddy.data.retrofit.model.toProductionCompanyDomainModel
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.toMovieWithProductionCompany
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.ChartType.*
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.model.Result.Success
import com.syrous.cinemabuddy.domain.usecases.FetchChartedMoviesUseCase
import com.syrous.cinemabuddy.utils.SystemConfigStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

@FlowPreview
class FetchChartedMoviesUseCaseImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesApi: MoviesApi,
    private val moviesWithGenreDao: MoviesWithGenreDao,
    private val chartedMoviesDao: ChartedMoviesDao,
    private val sysConfigStorage: SystemConfigStorage
): FetchChartedMoviesUseCase {

    private val _flowOfChartedMovies =
        MutableStateFlow<Result<List<MovieDomainModel>>>(Result.NotInitialized)

    private val _flowOfChartType = MutableStateFlow(UPCOMING)

    private val _flowOfPageNo = MutableStateFlow(10)

    override val flowOfChartedMovies: StateFlow<Result<List<MovieDomainModel>>> = _flowOfChartedMovies

    override suspend fun execute() {
        _flowOfChartedMovies.emit(Result.Loading)
        withContext(Dispatchers.IO) {
            _flowOfChartType.collect {
                when(it){
                    NORMAL -> TODO()
                    POPULAR -> {
                        try {
                            val movieResponse = moviesApi.getPopularMoviesList(
                                BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(), 1, null)

                            saveMoviesToLocalStorage(moviesDao, moviesWithGenreDao,
                                chartedMoviesDao, movieResponse, POPULAR
                            )
                            _flowOfChartedMovies.emit(Result.DoneLoading)
                        } catch (e: Exception) {
                            _flowOfChartedMovies.emit(Result.DoneLoading)
                            _flowOfChartedMovies.emit(Result.Error(e))
                            e.printStackTrace()
                        }
                    }
                    TOP_RATED -> {
                        try {
                            val movieResponse = moviesApi.getTopRatedMoviesList(
                                BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(), 1, null)

                            saveMoviesToLocalStorage(moviesDao, moviesWithGenreDao,
                                chartedMoviesDao, movieResponse, TOP_RATED
                            )
                            _flowOfChartedMovies.emit(Result.DoneLoading)
                        } catch (e: Exception) {
                            _flowOfChartedMovies.emit(Result.DoneLoading)
                            _flowOfChartedMovies.emit(Result.Error(e))
                            e.printStackTrace()
                        }
                    }
                    UPCOMING -> {
//                        try {
//                            val movieResponse = moviesApi.getUpcomingMoviesList(
//                                BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(), 1, null)
//
//                            saveMoviesToLocalStorage(moviesDao, moviesWithGenreDao,
//                                chartedMoviesDao, movieResponse, POPULAR
//                            )
//                            _flowOfChartedMovies.emit(Result.DoneLoading)
//                        } catch (e: Exception) {
//                            _flowOfChartedMovies.emit(Result.DoneLoading)
//                            _flowOfChartedMovies.emit(Result.Error(e))
//                            e.printStackTrace()
//                        }

                    }
                }
            }

        }
    }

    override suspend fun loadChartedMoviesFromLocalStorage() {
       _flowOfChartedMovies.emit(Result.Loading)
        _flowOfChartType.combine(_flowOfPageNo) {
            chartTypeFlow, pageFlow ->
            observeChartedMovies(chartTypeFlow, pageFlow)
        }.flattenConcat().collect {
            _flowOfChartedMovies.emit(Result.DoneLoading)
            _flowOfChartedMovies.emit(it)
        }
    }

    private suspend fun observeChartedMovies(chartType: ChartType, page: Int
    ): Flow<Result<List<MovieDomainModel>>> =
        chartedMoviesDao.getListOfChartedMovies(chartType, page).map {
                moviesList ->
            val movieDomainList = mutableListOf<MovieDomainModel>()
            for(movie in moviesList) {
                val genreList = moviesWithGenreDao.getGenreListForMovie(movie.id)
                movieDomainList.add(movie.toMovieDomainModel(genreList))
            }
            Success(movieDomainList.toList())
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

    @Throws(Exception::class)
    private suspend fun saveMoviesToLocalStorage(
        moviesDao: MoviesDao,
        moviesWithGenreDao: MoviesWithGenreDao,
        chartedMoviesDao: ChartedMoviesDao,
        movieResponse: MovieResponse,
        chartType: ChartType
    ){
        for (movie in movieResponse.movieModelList) {
            moviesDao.saveMovie(movie.toMovieDbModel())
            for(genre in movie.genreIdList) {
                moviesWithGenreDao.saveMovieWithGenre(movie.toMovieWithGenre(genre))
            }
            chartedMoviesDao.makeEntryForMovie(movie.toChartedMovie(chartType))
        }
    }

    private suspend fun fetchMovieDetails(
        movieId: Int,
        productionCompanyDao: ProductionCompanyDao,
        moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao
    ) {
        withContext(Dispatchers.IO) {
            val result = moviesApi.getMovieDetails(movieId, BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang())
            saveMovieWithProductionDetails(result, productionCompanyDao, moviesWithProductionCompanyDao)
        }
    }


    @Throws(Exception::class)
    private suspend fun saveMovieWithProductionDetails(
        movieDetails: MovieDetailResponse,
        productionCompanyDao: ProductionCompanyDao,
        moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao
    ) {
        for(productionCompany in movieDetails.productionCompanyList) {
            productionCompanyDao.saveProductionCompany(productionCompany
                .toProductionCompanyDomainModel(false))

            moviesWithProductionCompanyDao.saveMovieWithProductionCompany(
                movieDetails.toMovieWithProductionCompany(productionCompany.id))
        }
    }

}