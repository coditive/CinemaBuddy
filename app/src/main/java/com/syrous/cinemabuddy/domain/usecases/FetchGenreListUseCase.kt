package com.syrous.cinemabuddy.domain.usecases

import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import kotlinx.coroutines.flow.StateFlow

interface FetchGenreListUseCase: UseCase {

    val flowOfGenreList: StateFlow<Result<List<GenreDomainModel>>>

    suspend fun getGenreList()
}