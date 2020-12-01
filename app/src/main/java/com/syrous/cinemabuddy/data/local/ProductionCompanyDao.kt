package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.syrous.cinemabuddy.data.model.ProductionCompany
import com.syrous.cinemabuddy.domain.model.ProductionCompanyDomainModel
import dagger.multibindings.IntoSet

@Dao
interface ProductionCompanyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveProductionCompany(prodComp: ProductionCompanyDomainModel)
}