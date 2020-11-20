package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.model.MovieWithGenre
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesWithGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieWithGenre(movie: MovieWithGenre)

    @Query("SELECT  id, name, lang FROM genredomainmodel JOIN moviewithgenre ON id = genreId WHERE movieId = :movieId")
    suspend fun getGenreListForMovie(movieId: Int): List<GenreDomainModel>
}