package com.syrous.cinemabuddy.backgroundwork

import androidx.work.DelegatingWorkerFactory
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.utils.SystemConfigStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaBuddyWorkerFactory @Inject constructor(
    moviesApi: MoviesApi,
    moviesWithGenreDao: MoviesWithGenreDao,
    moviesDao: MoviesDao,
    chartedMoviesDao: ChartedMoviesDao,
    moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao,
    productionCompanyDao: ProductionCompanyDao,
    notificationDao: NotificationDao,
    systemConfigStorage: SystemConfigStorage
): DelegatingWorkerFactory() {
    init {
        addFactory(SubscriptionWorkFactory(moviesApi,
            moviesWithGenreDao,
            moviesDao,
            chartedMoviesDao,
            moviesWithProductionCompanyDao,
            productionCompanyDao,
            systemConfigStorage
        ))

        addFactory(NotificationWorkFactory(
            moviesDao,
            notificationDao
        ))
    }
}