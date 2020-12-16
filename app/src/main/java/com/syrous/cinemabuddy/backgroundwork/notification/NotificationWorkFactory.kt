package com.syrous.cinemabuddy.backgroundwork.notification

import android.content.Context
import android.os.Build
import androidx.work.*
import com.syrous.cinemabuddy.data.local.dao.MoviesDao
import com.syrous.cinemabuddy.data.local.dao.NotificationDao
import com.syrous.cinemabuddy.data.local.dao.ProductionCompanyDao
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import java.util.concurrent.TimeUnit

class NotificationWorkFactory(
    private val moviesDao: MoviesDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val systemConfigUseCase: SystemConfigUseCase,
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
             productionCompanyDao,
             notificationDao,
             systemConfigUseCase,
             appContext
         )
        }
        else null
}

fun Context.enqueueNotificationWorker(): WorkRequest {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) constraints.setRequiresDeviceIdle(true)

    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
        .setConstraints(constraints.build())
        .addTag(NotificationWorker.NOTIFICATION_TAG)
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        NotificationWorker.NOTIFICATION_TAG,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest)
    return workRequest
}
