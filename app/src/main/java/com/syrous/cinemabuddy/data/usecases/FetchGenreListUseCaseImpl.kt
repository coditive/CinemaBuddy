package com.syrous.cinemabuddy.data.usecases

import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.RetrofitResult
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.usecases.FetchGenreListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchGenreListUseCaseImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : FetchGenreListUseCase {

    private val _flowOfGenreList = MutableStateFlow<Result<List<GenreDomainModel>>>(Result.NotInitialized)
    override val flowOfGenreList: StateFlow<Result<List<GenreDomainModel>>> = _flowOfGenreList

    override suspend fun getGenreList() {
        localDataSource.observeGenreData().map {
                genreList -> Result.Success(genreList)
        }.collect {
                resultGenreList -> _flowOfGenreList.emit(resultGenreList)
        }
    }

    @Throws(Exception::class)
    override suspend fun execute() {
        _flowOfGenreList.emit(Result.Loading)
        withContext(Dispatchers.IO) {
            remoteDataSource.fetchGenreList().take(1).collect {
                    genreResponse ->
                _flowOfGenreList.emit(Result.DoneLoading)
                if(genreResponse is RetrofitResult.Success) {
                    localDataSource.saveGenreList(genreResponse.data)
                } else if(genreResponse is RetrofitResult.NetworkError){
                    _flowOfGenreList.emit(Result.NetworkError(genreResponse.exception))
                }
            }
        }
    }

}