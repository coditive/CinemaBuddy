package com.syrous.cinemabuddy.data.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*


@JsonClass(generateAdapter = true)
data class Dates (
    @Json(name = "maximum") val max: Date?,
    @Json(name = "minimum") val min: Date?
)