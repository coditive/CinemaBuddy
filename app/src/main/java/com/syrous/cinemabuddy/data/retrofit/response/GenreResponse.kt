package com.syrous.cinemabuddy.data.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.data.retrofit.model.GenreModel


@JsonClass(generateAdapter = true)
data class GenreResponse (
    @Json(name = "genres") val genreList: List<GenreModel>
)
