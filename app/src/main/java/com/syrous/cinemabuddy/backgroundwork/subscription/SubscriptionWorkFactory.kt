package com.syrous.cinemabuddy.backgroundwork.subscription

import android.content.Context
import android.os.Build
import androidx.work.*
import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.local.dao.*
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import java.util.concurrent.TimeUnit

class SubscriptionWorkFactory (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val systemConfigUseCase: SystemConfigUseCase,
    private val notificationDao: NotificationDao
    ): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        if (workerClassName == SubscriptionWorker::class.java.name)
            SubscriptionWorker(workerParameters,
                localDataSource,
                remoteDataSource,
                systemConfigUseCase,
                notificationDao,
                appContext)
        else null
}

fun Context.enqueueSubscriptionWorker(): WorkRequest {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) constraints.setRequiresDeviceIdle(true)

    val workRequest = PeriodicWorkRequestBuilder<SubscriptionWorker>(1, TimeUnit.DAYS)
        .setConstraints(constraints.build())
        .addTag(SubscriptionWorker.SUBSCRIPTION_TAG)
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        SubscriptionWorker.SUBSCRIPTION_TAG,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
    return workRequest
}

