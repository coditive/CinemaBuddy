package com.syrous.cinemabuddy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.model.toGenreDomainModel
import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.*
import com.syrous.cinemabuddy.domain.model.Result.*
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    override suspend fun fetchAndCacheGenreList(apiKey: String, lang: String) {
       withContext(Dispatchers.IO) {
           try {
               val genreResponse = moviesApi.getGenreList(apiKey, lang)
               for (genre in genreResponse.genreList) {
                   genreDao.saveGenreList(genre.toGenreDomainModel(lang))
               }
           } catch (e: Exception) {
                e.printStackTrace()
           }
       }
    }

    override fun observeGenreData(lang: String): LiveData<List<GenreDomainModel>> =
        genreDao.getGenreListForLang(lang)

    override suspend fun getMoviesFromLocalStorage() {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAndCacheTopRateMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): Result<Boolean> {
        TODO("Not yet implemented")
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