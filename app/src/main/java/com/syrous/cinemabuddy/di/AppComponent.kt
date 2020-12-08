package com.syrous.cinemabuddy.di

import android.app.Activity
import android.app.Application
import com.syrous.cinemabuddy.CinemaBuddyApplication
import com.syrous.cinemabuddy.presentation.MainActivity
import com.syrous.cinemabuddy.presentation.common.ActivityComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepoModule::class, AppSubcomponents::class, RoomModule::class, WorkManagerModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun activitySubcomponent(): ActivityComponent.Factory

    fun inject(cinemaBuddyApplication: CinemaBuddyApplication)
}