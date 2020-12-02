package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.data.local.MoviesDao
import com.syrous.cinemabuddy.data.local.NotificationDao

class NotificationWorker(
    workerParameters: WorkerParameters,
    private val moviesDao: MoviesDao,
    private val notificationDao: NotificationDao,
    private val context: Context
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}