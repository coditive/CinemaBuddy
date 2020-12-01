package com.syrous.cinemabuddy.utils

import android.content.SharedPreferences
import javax.inject.Inject

class SystemConfigStorage @Inject constructor(
    private val storage: SharedPreferences
) {
    fun updateSubscriptionWorkerSyncStartTime(time: Long) {
        storage.edit()
            .putLong(SUBSCRIPTION_WORKER_SYNC_START_TIME, time)
            .apply()
    }

    fun updateSubscriptionWorkerSyncEndTime(time: Long) {
        storage.edit()
            .putLong(SUBSCRIPTION_WORKER_SYNC_END_TIME, time)
            .apply()
    }

    fun getUserLang(): String = storage.getString(USER_LANGUAGE, "en-US")!!



}