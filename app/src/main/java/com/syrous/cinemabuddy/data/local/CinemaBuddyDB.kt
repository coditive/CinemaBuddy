package com.syrous.cinemabuddy.data.local

import androidx.room.*
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.ChartType


@Database(entities = [MovieDomainModel::class, ChartedMovies::class, GenreDomainModel::class, MoviesWithGenreList::class], version = 1, exportSchema = false)
abstract class CinemaBuddyDB: RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun moviesDao(): MoviesDao
    abstract fun chartedMoviesDao(): ChartedMoviesDao
    abstract fun moviesWithGenreDao(): MoviesWithGenreDao
}

@Entity(foreignKeys = [
        ForeignKey(
            entity = MovieDomainModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movieId"),
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    primaryKeys = ["movieId", "movieType"]
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