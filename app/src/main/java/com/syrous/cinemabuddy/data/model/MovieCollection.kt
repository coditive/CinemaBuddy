package com.syrous.cinemabuddy.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCollection (
    @Json(name = "id") val collectionId: Int,
    @Json(name = "name") val collectionName: String,
    @Json(name = "poster_path") val collectionPoster: String?,
    @Json(name = "backdrop_path") val collectionBackDrop: String?
    )