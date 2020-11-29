package com.syrous.cinemabuddy.di

import android.app.Application
import com.syrous.cinemabuddy.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepoModule::class, RoomModule::class, WorkManagerModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    @InternalCoroutinesApi
    fun inject(mainActivity: MainActivity)

    fun inject(application: Application)
}