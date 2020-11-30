package com.syrous.cinemabuddy.backgroundwork

import androidx.work.DelegatingWorkerFactory
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaBuddyWorkerFactory @Inject constructor(
    moviesApi: MoviesApi,
    moviesWithGenreDao: MoviesWithGenreDao,
    moviesDao: MoviesDao,
    chartedMoviesDao: ChartedMoviesDao,
    moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao,
    productionCompanyDao: ProductionCompanyDao
): DelegatingWorkerFactory() {
    init {
        addFactory(SubscriptionWorkFactory(moviesApi,
            moviesWithGenreDao,
            moviesDao,
            chartedMoviesDao,
            moviesWithProductionCompanyDao,
            productionCompanyDao
        ))
    }
}