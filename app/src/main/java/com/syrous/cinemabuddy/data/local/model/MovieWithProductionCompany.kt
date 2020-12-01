package com.syrous.cinemabuddy.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.syrous.cinemabuddy.domain.model.ProductionCompanyDomainModel


@Entity(
    primaryKeys = ["movieId", "productionCompanyId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movieId"),
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
        entity = ProductionCompanyDomainModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("productionCompanyId"),
        onDelete = ForeignKey.NO_ACTION)
    ]
)
data class MovieWithProductionCompany(
    val movieId: Int,
    val productionCompanyId: Int
    )
