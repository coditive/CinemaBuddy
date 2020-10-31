package com.syrous.cinemabuddy

import android.app.Application
import com.syrous.cinemabuddy.di.AppComponent
import com.syrous.cinemabuddy.di.DaggerAppComponent

class CinemaBuddyApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext as Application)
    }

    override fun onCreate() {
        super.onCreate()



    }
}