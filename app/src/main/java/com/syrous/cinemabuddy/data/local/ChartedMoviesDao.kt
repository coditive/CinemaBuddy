package com.syrous.cinemabuddy.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.ChartType

@Dao
interface ChartedMoviesDao {

    @Query("SELECT m.* FROM moviedomainmodel as m " +
            "INNER JOIN chartedmovies as t ON m.id = t.movieId")
    fun observeChartedMovies(chartType: ChartType): LiveData<List<MovieDomainModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChartedMovie(movie: ChartedMovies)
}