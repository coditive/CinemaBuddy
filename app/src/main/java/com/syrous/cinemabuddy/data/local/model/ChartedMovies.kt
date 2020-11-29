package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.domain.model.ChartType

@Entity(foreignKeys = [
    ForeignKey(
        entity = MovieDBModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"),
        onDelete = ForeignKey.NO_ACTION
    )
],
    primaryKeys = ["movieId", "chartType"]
)
data class ChartedMovies(
    val movieId: Int,
    val chartType: ChartType,
    val timestamp: Long
)