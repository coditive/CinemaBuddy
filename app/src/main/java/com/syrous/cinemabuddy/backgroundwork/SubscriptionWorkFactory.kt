package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import android.os.Build
import androidx.work.*
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.utils.SystemConfigStorage
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SubscriptionWorkFactory (
    private val moviesApi: MoviesApi,
    private val moviesWithGenreDao: MoviesWithGenreDao,
    private val moviesDao: MoviesDao,
    private val chartedMoviesDao: ChartedMoviesDao,
    private val moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val systemConfigStorage: SystemConfigStorage,
    private val notificationDao: NotificationDao
    ): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        if (workerClassName == SubscriptionWorker::class.java.name)
            SubscriptionWorker(workerParameters,
                moviesApi,
                chartedMoviesDao,
                moviesWithGenreDao,
                moviesDao,
                moviesWithProductionCompanyDao,
                productionCompanyDao,
                systemConfigStorage,
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
//        .setConstraints(constraints.build())
        .addTag(SubscriptionWorker.SUBSCRIPTION_TAG)
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        SubscriptionWorker.SUBSCRIPTION_TAG,
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
    return workRequest
}

