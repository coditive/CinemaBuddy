package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.model.MovieWithGenre
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesWithGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieWithGenre(movie: MovieWithGenre)

    @Query("SELECT * FROM moviewithgenre WHERE movieId = :movieId")
    suspend fun getGenreListForMovie(movieId: Int): Flow<List<MovieWithGenre>>
}