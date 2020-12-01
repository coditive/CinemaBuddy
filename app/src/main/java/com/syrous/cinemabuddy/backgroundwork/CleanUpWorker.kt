package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class CleanUpWorker(
    workerParameters: WorkerParameters,
    private val context: Context
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}