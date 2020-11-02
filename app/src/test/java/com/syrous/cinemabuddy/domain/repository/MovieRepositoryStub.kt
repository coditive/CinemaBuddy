package com.syrous.cinemabuddy.domain.repository

import com.syrous.cinemabuddy.domain.DomainConstant
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result

class MovieRepositoryStub : MovieRepository {
    override suspend fun fetchAndCacheTopRateMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): Result<List<MovieDomainModel>> =
        DomainConstant.fetchAndCacheTopRateMovies(apiKey, lang, page, region)

    override suspend fun fetchAndCachePopularMovies(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): Result<List<MovieDomainModel>> = DomainConstant.fetchAndCacheTopRateMovies(apiKey, lang, page, region)

}