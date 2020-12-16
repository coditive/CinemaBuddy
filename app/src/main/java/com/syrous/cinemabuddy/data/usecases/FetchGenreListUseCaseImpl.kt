package com.syrous.cinemabuddy.data.usecases

import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.local.dao.GenreDao
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.model.toGenreDomainModel
import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.usecases.FetchGenreListUseCase
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

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

                remoteDataSource.fetchGenreList().collect {
                        genreResponse ->
                    localDataSource.saveGenreList(genreResponse)
                }

                _flowOfGenreList.emit(Result.DoneLoading)
//            } catch (e: Exception) {
//                _flowOfGenreList.emit(Result.DoneLoading)
//                _flowOfGenreList.emit(Result.GeneralError(e))
//                e.printStackTrace()
//            }
        }
    }

}