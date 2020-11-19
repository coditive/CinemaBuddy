package com.syrous.cinemabuddy.data.local

import androidx.room.*
import com.syrous.cinemabuddy.data.local.TypeConverters.MovieTypeConverter
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.data.model.ChartedMovies
import com.syrous.cinemabuddy.data.model.MovieWithGenre
import com.syrous.cinemabuddy.domain.model.GenreDomainModel


@Database(entities = [MovieDBModel::class, ChartedMovies::class, GenreDomainModel::class, MovieWithGenre::class],
    version = 1,
    exportSchema = false)
@TypeConverters(MovieTypeConverter::class)
abstract class CinemaBuddyDB: RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun moviesDao(): MoviesDao
    abstract fun chartedMoviesDao(): ChartedMoviesDao
    abstract fun moviesWithGenreDao(): MoviesWithGenreDao
}


