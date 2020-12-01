package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenre(genre: GenreDomainModel)

    @Query("SELECT * FROM GenreDomainModel WHERE lang = :lang")
    fun observeGenreListForLang(lang: String): Flow<List<GenreDomainModel>>
}