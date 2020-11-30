package com.syrous.cinemabuddy.di

import android.app.Application
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
    fun provideDb(context: Application): CinemaBuddyDB {
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

    @Reusable
    @Provides
    fun provideGenreDao(cinemaBuddyDB: CinemaBuddyDB) = cinemaBuddyDB.genreDao()

    @Reusable
    @Provides
    fun provideMoviesDao(cinemaBuddyDB: CinemaBuddyDB) = cinemaBuddyDB.moviesDao()

    @Reusable
    @Provides
    fun provideMoviesWithGenreDao(cinemaBuddyDB: CinemaBuddyDB) = cinemaBuddyDB.moviesWithGenreDao()

    @Reusable
    @Provides
    fun provideMoviesWithProductionCompanyDao(cinemaBuddyDB: CinemaBuddyDB) = cinemaBuddyDB.moviesWithProductionCompanyDao()

    @Reusable
    @Provides
    fun provideProductionCompanyDao(cinemaBuddyDB: CinemaBuddyDB) = cinemaBuddyDB.productionCompanyDao()
}