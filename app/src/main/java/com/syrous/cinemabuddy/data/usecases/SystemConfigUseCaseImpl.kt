package com.syrous.cinemabuddy.data.usecases

import android.content.SharedPreferences
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import com.syrous.cinemabuddy.utils.SUBSCRIPTION_WORKER_SYNC_END_TIME
import com.syrous.cinemabuddy.utils.SUBSCRIPTION_WORKER_SYNC_START_TIME
import com.syrous.cinemabuddy.utils.USER_LANGUAGE
import com.syrous.cinemabuddy.utils.USER_REGION
import javax.inject.Inject

class SystemConfigUseCaseImpl @Inject constructor(
    private val storage: SharedPreferences
) : SystemConfigUseCase {
    override fun updateSubscriptionWorkerSyncStartTime(time: Long) {
        synchronized(storage) {
            storage.edit()
                .putLong(SUBSCRIPTION_WORKER_SYNC_START_TIME, time)
                .apply()
        }
    }

    override fun updateSubscriptionWorkerSyncEndTime(time: Long) {
        synchronized(storage) {
            storage.edit()
                .putLong(SUBSCRIPTION_WORKER_SYNC_END_TIME, time)
                .apply()
        }
    }

    override fun getSubscriptionWorkerSyncStartTime(): Long =
        storage.getLong(SUBSCRIPTION_WORKER_SYNC_START_TIME, 0)

    override fun getSubscriptionWorkerSyncEndTime(): Long =
        storage.getLong(SUBSCRIPTION_WORKER_SYNC_END_TIME, 0)

    override fun getUserLang(): String = storage.getString(USER_LANGUAGE, "en-US")!!
    override fun setUserLang(lang: String) {
        synchronized(storage) {
            storage.edit()
                .putString(USER_LANGUAGE, lang)
                .apply()
        }
    }

    override fun setUserRegion(region: String) {
        synchronized(storage) {
            storage.edit()
                .putString(USER_REGION, region)
                .apply()
        }
    }

    override fun getUserRegion(): String? = storage.getString(USER_REGION, null)
}