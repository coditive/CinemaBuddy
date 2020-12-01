package com.syrous.cinemabuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.domain.model.ProductionCompanyDomainModel


@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int,
    val name: String,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String
)

fun ProductionCompany.toProductionCompanyDomainModel(isSubscribed: Boolean
): ProductionCompanyDomainModel = ProductionCompanyDomainModel(
    id = id,
    name = name,
    logoPath = logoPath,
    originCountry = originCountry,
    isSubscribed = isSubscribed
)