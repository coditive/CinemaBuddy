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

    @Query("SELECT * FROM moviedbmodel WHERE id IN (SELECT movieId FROM chartedmovies WHERE chartType = 3 AND movieId IN (SELECT movieId FROM moviewithproductioncompany WHERE productionCompanyId =:prodComp))")
    suspend fun getUpcomingMoviesForAProductionCompany(prodComp: Int): List<MovieDBModel>

    @Query("SELECT * FROM productioncompanydomainmodel WHERE isSubscribed = 1")
    fun getSubscribedProductionCompaniesList(): Flow<ProductionCompanyDomainModel?>

    @Query("SELECT * FROM productioncompanydomainmodel WHERE id IN (SELECT productionCompanyId FROM MovieWithProductionCompany WHERE movieId = :movieId)")
    suspend fun getProductionCompanyForAMovie(movieId: Int): ProductionCompanyDomainModel
}