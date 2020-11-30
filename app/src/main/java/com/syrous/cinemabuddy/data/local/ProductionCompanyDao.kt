package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.syrous.cinemabuddy.data.model.ProductionCompany
import dagger.multibindings.IntoSet

@Dao
interface ProductionCompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProductionCompany(prodComp: ProductionCompany)
}