package com.syrous.cinemabuddy.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage (
    @Json(name = "iso_639_1") val languageCode: String,
    val name: String
    )