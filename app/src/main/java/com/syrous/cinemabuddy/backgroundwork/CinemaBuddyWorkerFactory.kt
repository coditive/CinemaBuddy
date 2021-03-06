package com.syrous.cinemabuddy.backgroundwork

import androidx.work.DelegatingWorkerFactory
import com.syrous.cinemabuddy.backgroundwork.notification.NotificationWorkFactory
import com.syrous.cinemabuddy.backgroundwork.subscription.SubscriptionWorkFactory
import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.local.dao.*
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaBuddyWorkerFactory @Inject constructor(
    localDataSource: LocalDataSource,
    remoteDataSource: RemoteDataSource,
    moviesDao: MoviesDao,
    productionCompanyDao: ProductionCompanyDao,
    notificationDao: NotificationDao,
    systemConfigUseCase: SystemConfigUseCase
): DelegatingWorkerFactory() {
    init {
        addFactory(
            SubscriptionWorkFactory(
                localDataSource,
                remoteDataSource,
                systemConfigUseCase,
                notificationDao
            )
        )

        addFactory(
            NotificationWorkFactory(
                moviesDao,
                productionCompanyDao,
                systemConfigUseCase,
                notificationDao
            )
        )
    }
}