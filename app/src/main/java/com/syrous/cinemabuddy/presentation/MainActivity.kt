package com.syrous.cinemabuddy.presentation

import android.os.Bundle
import com.syrous.cinemabuddy.CinemaBuddyApplication
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.presentation.common.controllers.BaseActivity
import kotlinx.coroutines.*
import javax.inject.Inject

@InternalCoroutinesApi
class MainActivity: BaseActivity() {

    @Inject
    lateinit var viewModel : HomeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as CinemaBuddyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

    }
}