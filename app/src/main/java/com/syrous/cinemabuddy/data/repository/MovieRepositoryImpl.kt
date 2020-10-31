package com.syrous.cinemabuddy.data.repository

import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.model.Result.*
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
) : MovieRepository {

    override suspend fun fetchAndCacheTopRateMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ):Result<List<MovieDomainModel>> {
      return try {
          Success(
              moviesApi.getTopRatedMoviesList(
              apiKey,
              lang,
              page,
              region
              )
              .movieDomainModelList
          )
        }catch (e: Exception) {
          Error(e)
        }
    }

    override suspend fun fetchAndCachePopularMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): Result<List<MovieDomainModel>> {
        return try {
            Success(
                moviesApi.getPopularMoviesList(
                    apiKey,
                    lang,
                    page,
                    region
                )
                    .movieDomainModelList
            )
        }catch (e: Exception) {
            Error(e)
        }
    }

}