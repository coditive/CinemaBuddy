package com.syrous.cinemabuddy.data.model

import com.squareup.moshi.JsonClass
import com.syrous.cinemabuddy.domain.model.GenreDomainModel


@JsonClass(generateAdapter = true)
data class GenreModel(
   val id: Int,
   val name: String
)


fun GenreModel.toGenreDomainModel(lang: String) =
    GenreDomainModel(
         id = this.id,
        name = this.name,
        lang = lang
    )