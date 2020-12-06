package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random


@Entity(primaryKeys = ["productionCompanyId", "movieId"])
data class NotificationDBModel (
    val id: Int = Random.nextInt(),
    val productionCompanyId: Int,
    val movieId: Int,
    val timestamp: Long,
    var isNotified: Boolean
    )