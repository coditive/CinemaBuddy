package com.syrous.cinemabuddy.data.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.data.retrofit.model.Dates
import com.syrous.cinemabuddy.data.retrofit.model.MovieModel

@JsonClass(generateAdapter = true)
data class UpcomingMovieResponse (
    val page: Int,
    @Json(name = "results") val movieModelList: List<MovieModel>,
    val dates: Dates,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results")val totalResults: Int
    )

fun UpcomingMovieResponse.toMovieResponse(): MovieResponse = MovieResponse(
    page = page,
    movieModelList = movieModelList,
    totalPages = totalPages,
    totalResults = totalResults
)