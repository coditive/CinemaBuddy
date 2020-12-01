package com.syrous.cinemabuddy.data.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountry (
    @Json(name = "iso_3166_1") val countryCode: String,
    @Json(name = "name") val countryName: String
    )