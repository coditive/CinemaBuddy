package com.syrous.cinemabuddy.data.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.data.retrofit.model.MovieModel


@JsonClass(generateAdapter = true)
data class MovieResponse (
    val page: Int,
    @Json(name = "results") val movieModelList: List<MovieModel>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results")val totalResults: Int
)