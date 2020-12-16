package com.syrous.cinemabuddy.data.local

import com.syrous.cinemabuddy.data.local.dao.*
import com.syrous.cinemabuddy.data.local.model.toMovieDomainModel
import com.syrous.cinemabuddy.data.retrofit.model.*
import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.toMovieWithProductionCompany
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.jvm.Throws

class LocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val genreDao: GenreDao,
    private val sysConfigUseCase: SystemConfigUseCase,
    private val moviesWithGenreDao: MoviesWithGenreDao,
    private val chartedMoviesDao: ChartedMoviesDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao
    ) : LocalDataSource {

    override fun observeGenreData(): Flow<List<GenreDomainModel>> =
        genreDao.observeGenreListForLang(sysConfigUseCase.getUserLang())

    override fun observeChartedMovies(
        chartType: ChartType,
        page: Int
    ): Flow<Result<List<MovieDomainModel>>> =
        chartedMoviesDao.getListOfChartedMovies(chartType, page * 10 - 10).map {
                moviesList ->
            val movieDomainList = mutableListOf<MovieDomainModel>()
            for(movie in moviesList) {
                val genreList = moviesWithGenreDao.getGenreListForMovie(movie.id)
                movieDomainList.add(movie.toMovieDomainModel(genreList))
            }
            Result.Success(movieDomainList.toList())
        }

    @Throws(Exception::class)
    override suspend fun saveMoviesToLocalStorage(
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


    @Throws(Exception::class)
    override suspend fun saveMovieWithProductionDetails(movieDetails: MovieDetailResponse) {
        for(productionCompany in movieDetails.productionCompanyList) {
            productionCompanyDao.saveProductionCompany(productionCompany
                .toProductionCompanyDomainModel(false))

            moviesWithProductionCompanyDao.saveMovieWithProductionCompany(
                movieDetails.toMovieWithProductionCompany(productionCompany.id))
        }
    }

    override suspend fun saveGenreList(genreResponse: GenreResponse) {
        for (genre in genreResponse.genreList) {
            genreDao.saveGenre(genre.toGenreDomainModel(sysConfigUseCase.getUserLang()))
        }
    }
}