package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.domain.model.MovieDomainModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDomainModel)

    @Query("SELECT * FROM moviedomainmodel WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieDomainModel?
}