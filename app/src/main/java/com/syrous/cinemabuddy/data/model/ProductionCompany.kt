package com.syrous.cinemabuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity
@JsonClass(generateAdapter = true)
data class ProductionCompany(
    @PrimaryKey val id: Int,
    val name: String,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String
)