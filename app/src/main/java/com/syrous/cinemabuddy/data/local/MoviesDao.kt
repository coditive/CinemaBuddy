package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDBModel)

    @Query("SELECT * FROM moviedbmodel WHERE id = :movieId")
    suspend fun getMovieFromDb(movieId: Int): MovieDBModel
}