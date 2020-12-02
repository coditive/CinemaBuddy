package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.data.local.MoviesDao
import com.syrous.cinemabuddy.data.local.NotificationDao

class NotificationWorkFactory(
    private val moviesDao: MoviesDao,
    private val notificationDao: NotificationDao
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        if(workerClassName == NotificationWorker::class.java.name) {
         NotificationWorker(
             workerParameters,
             moviesDao,
             notificationDao,
             appContext
         )
        }
        else null
}