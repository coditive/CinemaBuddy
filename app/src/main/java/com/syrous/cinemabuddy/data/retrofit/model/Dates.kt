package com.syrous.cinemabuddy.data.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Dates (
    @Json(name = "maximum") val max: String,
    @Json(name = "minimum") val min: String
)