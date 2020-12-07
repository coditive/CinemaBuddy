package com.syrous.cinemabuddy.backgroundwork.common

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.utils.NOTIFICATION_CHANNEL_ID
import com.syrous.cinemabuddy.utils.createNotificationChannel
import kotlin.random.Random

abstract class BaseWorker(workerParameters: WorkerParameters, context: Context)
    : CoroutineWorker (context, workerParameters){

    fun buildDebugNotificationAndShow(title: String, message: String, context: Context, id: Int = Random.nextInt()) {
        context.createNotificationChannel()
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        NotificationManagerCompat.from(context).notify(id, builder.build())
    }

    companion object {
        const val FAILURE_EXCEPTION = "failure_exception"
    }

}