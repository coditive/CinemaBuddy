package com.syrous.cinemabuddy.data.model

import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.domain.model.GenreDomainModel

fun GenreModel.toGenreDomainModel(lang: String) =
    GenreDomainModel(
        id = this.id,
        name = this.name,
        lang = lang
    )


fun MovieModel.toMovieDbModel(): MovieDBModel = MovieDBModel(
    id = this.id,
    title = this.title,
    originalTitle = this.originalTitle,
    overview = this.overview,
    isAdult = this.isAdult,
    releaseDate = this.releaseDate,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    originalLang = this.originalLang,
    video = this.video,
    voteAverage = this.voteAverage,
    popularity = this.popularity,
    voteCount = this.voteCount,
    createdAt = System.currentTimeMillis()
)
