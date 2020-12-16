package com.syrous.cinemabuddy.domain.usecases

interface SystemConfigUseCase {

    fun updateSubscriptionWorkerSyncStartTime(time: Long)

    fun updateSubscriptionWorkerSyncEndTime(time: Long)

    fun getSubscriptionWorkerSyncStartTime(): Long

    fun getSubscriptionWorkerSyncEndTime(): Long

    fun getUserLang(): String

    fun setUserLang(lang: String)

    fun setUserRegion(region: String)

    fun getUserRegion(): String?
}