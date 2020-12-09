package com.syrous.cinemabuddy.di

import com.syrous.cinemabuddy.data.usecases.FetchChartedMoviesUseCaseImpl
import com.syrous.cinemabuddy.data.usecases.FetchGenreListUseCaseImpl
import com.syrous.cinemabuddy.domain.usecases.FetchChartedMoviesUseCase
import com.syrous.cinemabuddy.domain.usecases.FetchGenreListUseCase
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.FlowPreview


@Module
abstract class UseCaseModule {
    @FlowPreview
    @Binds
    abstract fun FetchChartedMoviesUseCaseImpl(fetchChartedMoviesUseCaseImpl: FetchChartedMoviesUseCaseImpl
    ): FetchChartedMoviesUseCase

    @Binds
    abstract fun FetchGenreListUseCaseImpl(fetchGenreListUseCaseImpl: FetchGenreListUseCaseImpl
    ): FetchGenreListUseCase
}