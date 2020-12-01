package com.syrous.cinemabuddy.data.local

import androidx.room.*
import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.domain.model.ProductionCompanyDomainModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductionCompanyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveProductionCompany(prodComp: ProductionCompanyDomainModel)

    @Query("UPDATE productioncompanydomainmodel SET isSubscribed = 1 WHERE id = :prodCompId")
    suspend fun addSubscriptionForCompany(prodCompId: Int)

    @Query("UPDATE productioncompanydomainmodel SET isSubscribed = 0 WHERE id = :prodCompId")
    suspend fun removeSubscriptionForCompany(prodCompId: Int)

    @Query("SELECT * FROM moviedbmodel WHERE id IN (SELECT movieId FROM chartedmovies WHERE chartType = 3 AND movieId IN (SELECT DISTINCT movieId FROM moviewithproductioncompany WHERE productionCompanyId = :prodCompId))")
    fun getUpcomingMoviesForACompany(prodCompId: Int): Flow<List<MovieDBModel>>
}