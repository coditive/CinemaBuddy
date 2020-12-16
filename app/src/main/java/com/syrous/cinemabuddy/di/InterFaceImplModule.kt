package com.syrous.cinemabuddy.di

import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.local.LocalDataSourceImpl
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSourceImpl
import com.syrous.cinemabuddy.data.usecases.FetchChartedMoviesUseCaseImpl
import com.syrous.cinemabuddy.data.usecases.FetchGenreListUseCaseImpl
import com.syrous.cinemabuddy.data.usecases.SystemConfigUseCaseImpl
import com.syrous.cinemabuddy.domain.usecases.FetchChartedMoviesUseCase
import com.syrous.cinemabuddy.domain.usecases.FetchGenreListUseCase
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.FlowPreview


@Module
abstract class InterFaceImplModule {
    @FlowPreview
    @Binds
    abstract fun FetchChartedMoviesUseCaseImpl(fetchChartedMoviesUseCaseImpl: FetchChartedMoviesUseCaseImpl
    ): FetchChartedMoviesUseCase

    @Binds
    abstract fun FetchGenreListUseCaseImpl(fetchGenreListUseCaseImpl: FetchGenreListUseCaseImpl
    ): FetchGenreListUseCase

    @Binds
    abstract fun SystemConfigUseCaseImpl(systemConfigUseCaseImpl: SystemConfigUseCaseImpl)
    : SystemConfigUseCase

    @Binds
    abstract fun LocalDataSourceImpl(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun RemoteDataSourceImpl(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}