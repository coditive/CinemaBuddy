package com.syrous.cinemabuddy.data.repository

import androidx.lifecycle.LiveData
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.data.local.model.toMovieDomainModel
import com.syrous.cinemabuddy.data.model.*
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.*
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

@FlowPreview
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

    override fun observeChartedMovies(chartType: ChartType): Flow<List<MovieDomainModel>> = chartedMoviesDao
        .getListOfChartedMovies(chartType)
        .flatMapConcat { moviesDbList ->
            flow {
                val movieDomainList = mutableListOf<MovieDomainModel>()
                for(movie in moviesDbList) {
                    val genreList = moviesWithGenreDao.getGenreListForMovie(movie.id)
                    movieDomainList.add(movie.toMovieDomainModel(genreList))
                }
                emit(movieDomainList.toList())
            }
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
    ) {
        withContext(Dispatchers.IO) {
            try {
                val movieResponse = moviesApi.getPopularMoviesList(apiKey, lang, page, region)
                saveMovieToLocalStorage(movieResponse, ChartType.POPULAR)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}