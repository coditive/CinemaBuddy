package com.syrous.cinemabuddy.data.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GenreModel(
   @Json(name = "id")val id: Int,
   @Json(name = "name")val name: String
)


