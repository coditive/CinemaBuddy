package com.syrous.cinemabuddy.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.data.model.ChartedMovies
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.ChartType
import kotlinx.coroutines.flow.Flow

@Dao
interface ChartedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun makeEntryForMovie(chartedEntry: ChartedMovies)

    @Query("SELECT * FROM moviedbmodel as m JOIN chartedmovies ON id = movieId WHERE chartType = :chartType")
    fun getListOfChartedMovies(chartType: ChartType): Flow<List<MovieDBModel>>
}