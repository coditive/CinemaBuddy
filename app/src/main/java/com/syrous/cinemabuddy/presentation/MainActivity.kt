package com.syrous.cinemabuddy.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.syrous.cinemabuddy.CinemaBuddyApplication
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.backgroundwork.SubscriptionWorker
import com.syrous.cinemabuddy.backgroundwork.enqueueNotificationWorker
import com.syrous.cinemabuddy.backgroundwork.enqueueSubscriptionWorker
import kotlinx.coroutines.*
import javax.inject.Inject

@InternalCoroutinesApi
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModel : HomeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (this.application as CinemaBuddyApplication).appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()


        viewModel.getTheGenreList()

        viewModel.getPopularMovie()

        viewModel.getMovieDetails()

        val subWorkRequest = this.enqueueSubscriptionWorker()

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(subWorkRequest.id)
            .observe(this) {
                if((it != null)) {
                    val state = it.state
                    val myOutputData = it.tags
                    Log.d("SubWorkManagerInfoState", state.toString())
                    Log.d("SubWorkManagerInfo", it.toString())
                }
            }

        val notificationWorkRequest = this.enqueueNotificationWorker()

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(notificationWorkRequest.id)
            .observe(this) {
                if((it != null)) {
                    val state = it.state
                    val myOutputData = it.tags
                    Log.d("NotifyWorkManagerState", state.toString())
                    Log.d("NotifyWorkManagerInfo", it.toString())
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}