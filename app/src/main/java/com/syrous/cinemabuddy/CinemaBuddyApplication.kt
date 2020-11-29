package com.syrous.cinemabuddy

import android.app.Application
import androidx.work.Configuration
import com.syrous.cinemabuddy.di.AppComponent
import com.syrous.cinemabuddy.di.DaggerAppComponent
import javax.inject.Inject

class CinemaBuddyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workConfig: Configuration

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext as Application)
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun getWorkManagerConfiguration(): Configuration {
        TODO("Not yet implemented")
    }
}