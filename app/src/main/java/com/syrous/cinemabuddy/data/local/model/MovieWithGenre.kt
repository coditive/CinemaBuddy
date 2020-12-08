package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.syrous.cinemabuddy.domain.model.GenreDomainModel


@Entity(indices = [Index(value = arrayOf("genreId"), unique = true)],
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movieId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreDomainModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("genreId"),
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class MovieWithGenre(
    val movieId: Int,
    val genreId: Int
)