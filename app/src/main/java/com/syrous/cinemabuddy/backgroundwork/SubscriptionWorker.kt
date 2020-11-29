package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import javax.inject.Inject

class SubscriptionWorker(
    workerParameters: WorkerParameters,
    private val moviesApi: MoviesApi,
    private val context: Context
    ): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}
