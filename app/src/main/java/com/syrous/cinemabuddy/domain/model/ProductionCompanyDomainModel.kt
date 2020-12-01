package com.syrous.cinemabuddy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity
data class ProductionCompanyDomainModel (
    @PrimaryKey val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String,
    val isSubscribed: Boolean
    )