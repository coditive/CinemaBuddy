package com.syrous.cinemabuddy.backgroundwork.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.backgroundwork.common.BaseWorker
import com.syrous.cinemabuddy.data.local.dao.MoviesDao
import com.syrous.cinemabuddy.data.local.dao.NotificationDao
import com.syrous.cinemabuddy.data.local.dao.ProductionCompanyDao
import com.syrous.cinemabuddy.utils.SUBSCRIPTION_NOTIFICATION_CHANNEL_ID
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import com.syrous.cinemabuddy.utils.createSubscriptionNotificationChannel
import kotlinx.coroutines.flow.collect
import kotlin.random.Random

class NotificationWorker(
    workerParameters: WorkerParameters,
    private val moviesDao: MoviesDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val notificationDao: NotificationDao,
    private val systemConfigUseCase: SystemConfigUseCase,
    private val context: Context
): BaseWorker(workerParameters, context) {
    override suspend fun doWork(): Result {
        return try {
            productionCompanyDao.getSubscribedProductionCompaniesList().collect { prodComp ->
                if (prodComp != null) {
                    val notificationList = notificationDao
                        .getUnNotifiedNotificationWithInTimeRangeForAComp(prodComp.id)

                    if (notificationList != null) {
                        for(notification in notificationList) {
                            val movie = moviesDao.getMovieFromDb(notification.movieId)
                            buildSubNotificationAndShow("${prodComp.name} Notification",
                                "${prodComp.name}'s upcoming movie is ${movie.originalTitle}"
                                , context)

                            notificationDao.updateNotificationNotified(notification.id)
                        }
                    }
                }

            }

            Result.success()
        } catch (e: Exception) {

            val failureOutput = Data.Builder()
                .putString(FAILURE_EXCEPTION, e.message)
                .build()

            buildDebugNotificationAndShow("Error In Notification Worker", e.message!!, context)

            Result.failure(failureOutput)
        }

    }

    private fun buildSubNotificationAndShow(title: String, message: String,
                                            context: Context,
                                            id: Int = Random.nextInt()) {
        context.createSubscriptionNotificationChannel()
        val builder = NotificationCompat.Builder(context, SUBSCRIPTION_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        NotificationManagerCompat.from(context).notify(id, builder.build())
    }

    companion object {
        const val NOTIFICATION_TAG = "notification_tag"
    }

    //TODO : change this worker implementation to use alarm manager for timing this work.
}