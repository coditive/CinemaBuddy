package com.syrous.cinemabuddy.data.local

import androidx.room.*
import com.syrous.cinemabuddy.data.local.TypeConverters.MovieTypeConverter
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.ChartType


@Database(entities = [MovieDBModel::class, ChartedMovies::class, GenreDomainModel::class, MoviesWithGenreList::class], version = 1, exportSchema = false)
@TypeConverters(MovieTypeConverter::class)
abstract class CinemaBuddyDB: RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun moviesDao(): MoviesDao
    abstract fun chartedMoviesDao(): ChartedMoviesDao
    abstract fun moviesWithGenreDao(): MoviesWithGenreDao
}

@Entity(foreignKeys = [
        ForeignKey(
            entity = MovieDBModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movieId"),
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    primaryKeys = ["movieId", "chartType"]
)
data class ChartedMovies(
    val movieId: Int,
    val chartType: ChartType,
    val timestamp: Long
)

@Entity(primaryKeys = ["movieId", "genreId"])
data class MoviesWithGenreList(
    val movieId: Int,
    val genreId: Int
)