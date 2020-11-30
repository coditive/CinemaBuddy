package com.syrous.cinemabuddy.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.syrous.cinemabuddy.CinemaBuddyApplication
import com.syrous.cinemabuddy.R
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
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}