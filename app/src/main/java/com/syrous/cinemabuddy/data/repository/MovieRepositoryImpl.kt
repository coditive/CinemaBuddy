package com.syrous.cinemabuddy.data.repository

import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val moviesApi: MoviesApi
) : MovieRepository {
    override suspend fun getTopRatedMoviesFromLocal(): List<MovieDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAndSaveTopRateMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAndSavePopularMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMoviesFromLocal(): List<MovieDomainModel> {
        TODO("Not yet implemented")
    }

}