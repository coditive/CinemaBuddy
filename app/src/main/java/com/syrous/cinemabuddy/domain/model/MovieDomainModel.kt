package com.syrous.cinemabuddy.domain.model


import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.data.model.ChartedMovies


data class MovieDomainModel (
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String?,
    val isAdult: Boolean,
    val releaseDate: String,
    val posterPath: String?,
    val backdropPath: String?,
    val originalLang: String,
    val genreIdList: List<GenreDomainModel>?,
    var video: Boolean,
    var voteAverage: Double,
    var popularity: Double,
    var voteCount: Int,
    var createdAt: Long
)

fun MovieDomainModel.toChartedMovies(chartType: ChartType): ChartedMovies = ChartedMovies(
    movieId = this.id,
    chartType = chartType,
    timestamp = System.currentTimeMillis()
)

fun MovieDomainModel.toMovieDbModel(): MovieDBModel = MovieDBModel(
    id = id,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    isAdult = isAdult,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalLang = originalLang,
    video = video,
    voteAverage = voteAverage,
    popularity = popularity,
    voteCount = voteCount,
    createdAt = createdAt
)