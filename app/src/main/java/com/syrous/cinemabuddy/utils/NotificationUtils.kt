package com.syrous.cinemabuddy.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.syrous.cinemabuddy.R


fun Context.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            getString(R.string.debug_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            notificationManager.createNotificationChannel(this)
        }
    }
}

fun Context.createSubscriptionNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationChannel(
            SUBSCRIPTION_NOTIFICATION_CHANNEL_ID,
            getString(R.string.sub_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            notificationManager.createNotificationChannel(this)
        }
    }
}