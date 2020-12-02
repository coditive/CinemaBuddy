package com.syrous.cinemabuddy.data.retrofit.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieModel(
    val id: Int,
    val title: String,
    @Json(name = "original_title") val originalTitle: String,
    val overview: String?,
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "release_date") val releaseDate: Date?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "original_language") val originalLang: String,
    @Json(name = "genre_ids") val genreIdList: List<Int>,
    val video: Boolean,
    val popularity: Double,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)
