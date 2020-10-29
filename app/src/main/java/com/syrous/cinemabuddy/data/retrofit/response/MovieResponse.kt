package com.syrous.cinemabuddy.data.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.domain.model.MovieDomainModel


@JsonClass(generateAdapter = true)
class MovieResponse (
    val page: Int,
    @Json(name = "results") val movieDomainModelList: List<MovieDomainModel>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results")val totalResults: Int
)