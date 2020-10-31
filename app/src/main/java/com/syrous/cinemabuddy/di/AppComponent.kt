package com.syrous.cinemabuddy.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component


@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}