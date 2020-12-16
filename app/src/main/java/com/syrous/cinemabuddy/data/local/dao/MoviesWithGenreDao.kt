package com.syrous.cinemabuddy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.local.model.MovieWithGenre
import com.syrous.cinemabuddy.domain.model.GenreDomainModel


@Dao
interface MoviesWithGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovieWithGenre(movie: MovieWithGenre)

    @Query("SELECT  id, name, lang FROM genredomainmodel JOIN moviewithgenre ON id = genreId WHERE movieId = :movieId")
    suspend fun getGenreListForMovie(movieId: Int): List<GenreDomainModel>
}