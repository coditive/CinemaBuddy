package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Notification (
    @PrimaryKey val id: Int,
    val productionCompanyName: String,
    val movieId: Int,
    val message: String,
    val timestamp: Long
    )