package com.syrous.cinemabuddy.di


import com.syrous.cinemabuddy.data.repository.MovieRepositoryImpl
import com.syrous.cinemabuddy.domain.repository.MovieRepository

import dagger.Binds
import dagger.Module
import kotlinx.coroutines.FlowPreview


@Module
abstract class RepoModule {
    @FlowPreview
    @Binds
    abstract fun MovieRepositoryImpl(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}