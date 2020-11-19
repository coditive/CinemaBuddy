package com.syrous.cinemabuddy.data.repository

import androidx.lifecycle.LiveData
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.model.*
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.*
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 *   Todo List for Next Day
 *
 *    Use Room's @relation feature to again model the database schema
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

    override suspend fun fetchAndCacheGenreList(apiKey: String, lang: String) {
       withContext(Dispatchers.IO) {
           try {
               val genreResponse = moviesApi.getGenreList(apiKey, lang)
               for (genre in genreResponse.genreList) {
                   genreDao.saveGenre(genre.toGenreDomainModel(lang))
               }
           } catch (e: Exception) {
                e.printStackTrace()
           }
       }
    }

    override fun observeGenreData(lang: String): Flow<List<GenreDomainModel>> =
        genreDao.observeGenreListForLang(lang)

    override fun observeChartedMovies(chartType: ChartType): LiveData<List<MovieDomainModel>> {
        TODO()
    }

    override suspend fun fetchAndCacheTopRateMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ) {
        withContext(Dispatchers.IO) {
            try {
                val movieResponse = moviesApi.getTopRatedMoviesList(apiKey, lang, page, region)
                saveMovieToLocalStorage(movieResponse, ChartType.TOP_RATED)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(Exception::class)
    private suspend fun saveMovieToLocalStorage(movieResponse: MovieResponse, chartType: ChartType){
        for (movie in movieResponse.movieModelList) {
            moviesDao.saveMovie(movie.toMovieDbModel())
            for(genre in movie.genreIdList) {
                moviesWithGenreDao.saveMovieWithGenre(movie.toMovieWithGenre(genre))
            }
            chartedMoviesDao.makeEntryForMovie(movie.toChartedMovie(chartType))
        }
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