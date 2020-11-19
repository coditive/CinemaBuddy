package com.syrous.cinemabuddy.data.model

import androidx.room.Entity


@Entity(primaryKeys = ["movieId", "genreId"])
data class MovieWithGenre(
    val movieId: Int,
    val genreId: Int
)