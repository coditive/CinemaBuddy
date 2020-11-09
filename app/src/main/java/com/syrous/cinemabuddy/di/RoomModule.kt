package com.syrous.cinemabuddy.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.syrous.cinemabuddy.data.local.CinemaBuddyDB
import com.syrous.cinemabuddy.utils.CINEMA_BUDDY_DB
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class RoomModule {
    private lateinit var cinemaBuddyDB: CinemaBuddyDB

    @Provides
    @Singleton
    fun provideDb(context: Context): CinemaBuddyDB {
        cinemaBuddyDB = Room.databaseBuilder(
            context.applicationContext,
            CinemaBuddyDB::class.java,
            CINEMA_BUDDY_DB
        ).build()
        return cinemaBuddyDB
    }

    @Reusable
    @Provides
    fun provideTopRatedMoviesDao(cinemaBuddyDB: CinemaBuddyDB) = cinemaBuddyDB.chartedMoviesDao()
}