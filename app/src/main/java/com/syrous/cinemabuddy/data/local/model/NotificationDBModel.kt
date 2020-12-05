package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NotificationDBModel (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val productionCompanyId: Int,
    val movieId: Int,
    val timestamp: Long,
    var isNotified: Boolean
    )