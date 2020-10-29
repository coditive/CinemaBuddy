package com.syrous.cinemabuddy.data

import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import retrofit2.Call
import retrofit2.http.Query

object ConstantData {

    fun getPopularMoviesList(
        apiKey: String,
        lang: String,
        page: Int,
        region: String?
    ): MovieResponse = MovieResponse(
        1,
            listOf(getMovie()),
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
        1231
    )


}