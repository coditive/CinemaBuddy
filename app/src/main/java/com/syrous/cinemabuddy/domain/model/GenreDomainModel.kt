package com.syrous.cinemabuddy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class GenreDomainModel(
    @PrimaryKey val id: Int,
    val name: String,
    val lang: String?
)