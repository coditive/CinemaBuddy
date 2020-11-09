package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.domain.model.GenreDomainModel

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenreInTable(genre: GenreDomainModel)

    @Query("SELECT * FROM genredomainmodel WHERE lang = :lang")
    suspend fun getGenreListForLanguage(lang: String): List<GenreDomainModel>
}