package com.syrous.cinemabuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syrous.cinemabuddy.domain.model.MovieDomainModel


@Database(entities = [MovieDomainModel::class], version = 1, exportSchema = false)
abstract class CinemaBuddyDB: RoomDatabase() {
    


}