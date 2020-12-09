package com.syrous.cinemabuddy.data.usecases

import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.local.GenreDao
import com.syrous.cinemabuddy.data.retrofit.model.toGenreDomainModel
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.usecases.FetchGenreListUseCase
import com.syrous.cinemabuddy.utils.SystemConfigStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchGenreListUseCaseImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val genreDao: GenreDao,
    private val sysConfigStorage: SystemConfigStorage
) : FetchGenreListUseCase {

    private val _flowOfGenreList = MutableStateFlow<Result<List<GenreDomainModel>>>(Result.NotInitialized)
    override val flowOfGenreList: StateFlow<Result<List<GenreDomainModel>>> = _flowOfGenreList

    override suspend fun getGenreList() {
       observeGenreData(sysConfigStorage.getUserLang()).map {
           genreList -> Result.Success(genreList)
       }.collect {
           resultGenreList -> _flowOfGenreList.emit(resultGenreList)
       }
    }

    override suspend fun execute() {
        _flowOfGenreList.emit(Result.Loading)
        withContext(Dispatchers.IO) {
            try {
                val genreResponse = moviesApi.getGenreList(BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang())
                for (genre in genreResponse.genreList) {
                    genreDao.saveGenre(genre.toGenreDomainModel(sysConfigStorage.getUserLang()))
                }
                _flowOfGenreList.emit(Result.DoneLoading)
            } catch (e: Exception) {
                _flowOfGenreList.emit(Result.DoneLoading)
                _flowOfGenreList.emit(Result.Error(e))
                e.printStackTrace()
            }
        }
    }

    private fun observeGenreData(lang: String): Flow<List<GenreDomainModel>> =
        genreDao.observeGenreListForLang(lang)
}