package com.syrous.cinemabuddy.presentation.common

import android.app.Activity
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ViewMVCModule {

    @Provides
    fun provideViewMVCFactory(activity: Activity): ViewMVCFactory {
        return ViewMVCFactory(LayoutInflater.from(activity))
    }

}