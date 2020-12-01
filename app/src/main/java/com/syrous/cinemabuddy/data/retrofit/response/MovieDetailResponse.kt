package com.syrous.cinemabuddy.data.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.data.local.model.MovieWithProductionCompany
import com.syrous.cinemabuddy.data.retrofit.model.*

@JsonClass(generateAdapter = true)
data class MovieDetailResponse (
    @Json(name = "belongs_to_collection") val collectionList: MovieCollection?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "genres") val genreList: List<GenreModel>,
    val homepage: String?,
    val id: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_language") val originalLang: String,
    @Json(name = "original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanyList: List<ProductionCompany>,
    @Json(name = "production_countries") val productionCountryList: List<ProductionCountry>,
    val revenue: Int,
    val runtime: Int?,
    @Json(name = "spoken_languages") val languageList: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
    )

fun MovieDetailResponse.toMovieWithProductionCompany(productionCompanyId: Int
): MovieWithProductionCompany = MovieWithProductionCompany(
    movieId = this.id,
    productionCompanyId = productionCompanyId,
)