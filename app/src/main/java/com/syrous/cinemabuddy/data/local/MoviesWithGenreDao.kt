package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.domain.model.MovieDomainModel


@Dao
interface MoviesWithGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMoviesWithGenre(movies: MoviesWithGenreList)

    @Query("SELECT * FROM movieswithgenrelist WHERE movieId = :movieId")
    suspend fun getGenreListForMovie(movieId: Int): List<MoviesWithGenreList>

}