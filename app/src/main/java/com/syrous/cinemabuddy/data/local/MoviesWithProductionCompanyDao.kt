package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.syrous.cinemabuddy.data.local.model.MovieWithProductionCompany


@Dao
interface MoviesWithProductionCompanyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovieWithProductionCompany(movieWithProductionCompany: MovieWithProductionCompany)


}