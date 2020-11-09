package com.syrous.cinemabuddy.domain

import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.DataConstant
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result

object DomainConstant {

    fun fetchAndCacheTopRateMovies(
        apiKey: String = "Sample_Api_Key",
        lang: String = "en-us",
        page: Int = 1,
        region: String? = "sample_region"
    ): Result<List<MovieDomainModel>> {
        return try {
            Result.Success(
            listOf(getMovie())
            )
        }catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getTopRatedMoviesList(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): MovieResponse = MovieResponse(
        1,
        listOf(DataConstant.getMovie()),
        10,
        10
    )

    fun getMovie(): MovieDomainModel = MovieDomainModel(
        102,
        "Sample Title",
        "Sample original title",
        "Sample overview",
        false,
        "Sample release date in format dd-mm-yyyy",
        "Sample poster url",
        "Sample backdrop url",
        "Sample Language",
        listOf(1,2,3),
        false,
        1235.112,
        124.234,
        1231,
        15646464384
    )

}