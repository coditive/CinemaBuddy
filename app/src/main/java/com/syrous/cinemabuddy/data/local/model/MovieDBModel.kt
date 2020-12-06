package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt


@Entity
data class MovieDBModel(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String?,
    val isAdult: Boolean,
    val releaseDate: Date?,
    val posterPath: String?,
    val backdropPath: String?,
    val originalLang: String,
    var video: Boolean,
    var voteAverage: Double,
    var popularity: Double,
    var voteCount: Int,
    var createdAt: Long
)

fun MovieDBModel.toMovieDomainModel(genreList: List<GenreDomainModel>)
: MovieDomainModel = MovieDomainModel(
    id = id,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    isAdult = isAdult,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalLang = originalLang,
    genreIdList = genreList,
    video = video,
    voteAverage = voteAverage,
    popularity = popularity,
    voteCount = voteCount,
    createdAt = createdAt
)

fun MovieDBModel.toNotificationDBModel(productionCompanyId: Int)
: NotificationDBModel = NotificationDBModel(
    productionCompanyId = productionCompanyId,
    movieId = this.id,
    timestamp = this.createdAt,
    isNotified = false
)