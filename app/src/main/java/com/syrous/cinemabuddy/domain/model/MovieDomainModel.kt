package com.syrous.cinemabuddy.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.syrous.cinemabuddy.data.local.ChartedMovies

@Entity
data class MovieDomainModel (
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String?,
    val isAdult: Boolean,
    val releaseDate: String,
    val posterPath: String?,
    val backdropPath: String?,
    val originalLang: String,
    @Ignore val genreIdList: List<Int>?,
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