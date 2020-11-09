package com.syrous.cinemabuddy.data.repository

import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.model.toGenreDomainModel
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.*
import com.syrous.cinemabuddy.domain.model.Result.*
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 *   Todo List for Next Day
 *
 *      Use Room's @relation feature to again model the database schema
 *
 *    Implement saveChartedMoviesInTables() To differentiate entry of genre in genre table while saving in local storage
 *
 *    Implement function to combine the result of database fetch for movies list and genre it's in and deliver whole MovieDomainModel Object
 *
 *    Check for the sql statement for ChartedMoviesDao observe function to get the correct domain object.
 *
 */


class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val genreDao: GenreDao,
    private val moviesDao: MoviesDao,
    private val chartedMoviesDao: ChartedMoviesDao,
    private val moviesWithGenreDao: MoviesWithGenreDao
) : MovieRepository {

    override suspend fun fetchAndCacheGenreList(apiKey: String, lang: String): Result<Boolean> {
        return try {
            val genreList = moviesApi.getGenreList(
                apiKey,
                lang
            ).genreList
            for (genre in genreList) {
                genreDao.insertGenreInTable(genre.toGenreDomainModel(lang))
            }
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getMoviesFromLocalStorage() {

    }

    override suspend fun fetchAndCacheTopRateMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): Result<Boolean> {
      return try {
          val moviesList = moviesApi.getTopRatedMoviesList(
             apiKey,
              lang,
              page,
              region
          ).movieDomainModelList

          for (movie in moviesList) {
              saveChartedMoviesInTables(movie, ChartType.TOP_RATED)
          }
          Success(true)
        } catch (e: Exception) {
          e.printStackTrace()
          Error(e)
        }
    }

    @Throws(Exception::class)
    private suspend fun saveChartedMoviesInTables(movie: MovieDomainModel, chartType: ChartType) {
        for (genre in movie.genreIdList!!) {
            moviesWithGenreDao.saveMoviesWithGenre(MoviesWithGenreList(movie.id, genre))
        }
        chartedMoviesDao.saveChartedMovie(movie.toChartedMovies(chartType))
        moviesDao.saveMovie(movie)
    }

    override suspend fun fetchAndCachePopularMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }


}