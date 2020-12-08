package com.syrous.cinemabuddy.presentation.common

import android.app.Activity
import com.syrous.cinemabuddy.presentation.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent
import kotlinx.coroutines.InternalCoroutinesApi

@Subcomponent(modules = [ViewMVCModule::class])
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): ActivityComponent
    }


    @InternalCoroutinesApi
    fun inject(mainActivity: MainActivity)
}