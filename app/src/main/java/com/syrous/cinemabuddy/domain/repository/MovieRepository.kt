package com.syrous.cinemabuddy.domain.repository

import com.syrous.cinemabuddy.domain.model.MovieDomainModel

internal interface MovieRepository {

    suspend fun getTopRatedMoviesFromLocal(): List<MovieDomainModel>

    suspend fun fetchAndSaveTopRateMovies(apiKey: String, lang: String, page: Int, region: String?)

    suspend fun fetchAndSavePopularMovies(apiKey: String, lang: String, page: Int, region: String?)

    suspend fun getPopularMoviesFromLocal(): List<MovieDomainModel>
}