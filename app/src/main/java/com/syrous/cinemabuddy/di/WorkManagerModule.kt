package com.syrous.cinemabuddy.di


import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.syrous.cinemabuddy.backgroundwork.CinemaBuddyWorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class WorkManagerModule {

    @Provides
    fun provideWorkConfiguration(cinemaBuddyWorkerFactory: CinemaBuddyWorkerFactory)
    : Configuration = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .setWorkerFactory(cinemaBuddyWorkerFactory)
        .build()

    @Provides
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
}