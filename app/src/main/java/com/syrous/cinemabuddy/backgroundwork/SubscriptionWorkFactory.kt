package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import javax.inject.Inject

class SubscriptionWorkFactory @Inject constructor(
    private val moviesApi: MoviesApi
    ): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        if (workerClassName == SubscriptionWorker::class.java.name)
            SubscriptionWorker(workerParameters, moviesApi, appContext)
        else null

}

