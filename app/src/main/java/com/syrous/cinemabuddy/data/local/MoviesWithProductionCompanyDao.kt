package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.syrous.cinemabuddy.data.local.model.MovieWithProductionCompany


@Dao
interface MoviesWithProductionCompanyDao {

    @Insert
    suspend fun saveMovieWithProductionCompany(movieWithProductionCompany: MovieWithProductionCompany)


}