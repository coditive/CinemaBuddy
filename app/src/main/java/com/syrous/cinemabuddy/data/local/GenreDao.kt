package com.syrous.cinemabuddy.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.domain.model.GenreDomainModel

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenreList(genreList: GenreDomainModel)

    @Query("SELECT * FROM GenreDomainModel WHERE lang = :lang")
    fun getGenreListForLang(lang: String): LiveData<List<GenreDomainModel>>

}