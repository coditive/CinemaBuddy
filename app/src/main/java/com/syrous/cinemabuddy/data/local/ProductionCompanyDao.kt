package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.syrous.cinemabuddy.data.model.ProductionCompany
import dagger.multibindings.IntoSet

@Dao
interface ProductionCompanyDao {

    @Insert
    suspend fun saveProductionCompany(prodComp: ProductionCompany)



}