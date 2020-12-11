package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.data.local.model.ChartedMovies
import com.syrous.cinemabuddy.domain.model.ChartType
import kotlinx.coroutines.flow.Flow

@Dao
interface ChartedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun makeEntryForMovie(chartedEntry: ChartedMovies)

    @Query("SELECT * FROM moviedbmodel as m JOIN chartedmovies ON id = movieId WHERE chartType = :chartType LIMIT :offset,10 ")
    fun getListOfChartedMovies(chartType: ChartType, offset: Int): Flow<List<MovieDBModel>>
}