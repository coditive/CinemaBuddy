package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity


@Entity(primaryKeys = ["movieId", "productionCompanyId"])
data class MovieWithProductionCompany(
    val movieId: Int,
    val productionCompanyId: Int
)
