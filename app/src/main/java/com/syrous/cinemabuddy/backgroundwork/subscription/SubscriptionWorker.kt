package com.syrous.cinemabuddy.backgroundwork.subscription

import android.content.Context
import androidx.work.Data
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.backgroundwork.common.BaseWorker
import com.syrous.cinemabuddy.data.local.LocalDataSource
import com.syrous.cinemabuddy.data.local.dao.*
import com.syrous.cinemabuddy.data.local.model.toNotificationDBModel
import com.syrous.cinemabuddy.data.retrofit.RemoteDataSource
import com.syrous.cinemabuddy.data.retrofit.model.toChartedMovie
import com.syrous.cinemabuddy.data.retrofit.model.toMovieDbModel
import com.syrous.cinemabuddy.data.retrofit.model.toMovieWithGenre
import com.syrous.cinemabuddy.data.retrofit.model.toProductionCompanyDomainModel
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.UpcomingMovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.toMovieWithProductionCompany
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import kotlinx.coroutines.flow.collect
import kotlin.jvm.Throws

class SubscriptionWorker(
    workerParameters: WorkerParameters,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val systemConfigUseCase: SystemConfigUseCase,
    private val notificationDao: NotificationDao,
    private val context: Context
    ): BaseWorker(workerParameters, context) {
    override suspend fun doWork(): Result {
       return try {
           systemConfigUseCase.updateSubscriptionWorkerSyncStartTime(System.currentTimeMillis())
//
//           val result = moviesApi.getUpcomingMoviesList(BuildConfig.API_KEY_V3,
//               systemConfigUseCase.getUserLang(),1, null)

//           saveMoviesToLocalStorage(result, ChartType.UPCOMING)
//
//           for(movie in result.movieModelList) {
//               val movieDetails = moviesApi.getMovieDetails(movie.id, BuildConfig.API_KEY_V3,
//                   systemConfigUseCase.getUserLang())
//
//               saveMovieWithProductionDetails(movieDetails)
//           }
//
//
//           for(i in 2..result.totalPages) {
//                val response = moviesApi.getUpcomingMoviesList(BuildConfig.API_KEY_V3,
//                systemConfigUseCase.getUserLang(), i, null)
//
//               saveMoviesToLocalStorage(response, ChartType.UPCOMING)
//
//               for(movie in response.movieModelList) {
//                   val movieDetails = moviesApi.getMovieDetails(movie.id, BuildConfig.API_KEY_V3,
//                       systemConfigUseCase.getUserLang())
//
//                   saveMovieWithProductionDetails(movieDetails)
//               }
//           }

           systemConfigUseCase.updateSubscriptionWorkerSyncEndTime(System.currentTimeMillis())

           buildDebugNotificationAndShow("Finished Subscription Saving in DB",
               "Upcoming Movies are stored in DB with production Companies", context)
//
//           productionCompanyDao.getSubscribedProductionCompaniesList().collect {
//               prodComp ->
//               if(prodComp != null) {
//                   val moviesList =
//                       productionCompanyDao.getUpcomingMoviesForAProductionCompany(prodComp.id)
//                   for (movie in moviesList) {
//                       notificationDao.saveNotification(
//                           movie.toNotificationDBModel(prodComp.id)
//                       )
//                   }
//               }
//           }
           Result.success()
       } catch (e: Exception) {
            val failureOutput = Data.Builder()
                .putString(FAILURE_EXCEPTION, e.message)
                .build()

           buildDebugNotificationAndShow("Error In Subscription Worker", e.message!!, context)

            Result.failure(failureOutput)
        }
    }



    @Throws(Exception::class)
    private suspend fun saveMoviesToLocalStorage(movieResponse: UpcomingMovieResponse, chartType: ChartType) {
//        for (movie in movieResponse.movieModelList) {
//            moviesDao.saveMovie(movie.toMovieDbModel())
//            for(genre in movie.genreIdList) {
//                moviesWithGenreDao.saveMovieWithGenre(movie.toMovieWithGenre(genre))
//            }
//            chartedMoviesDao.makeEntryForMovie(movie.toChartedMovie(chartType))
//        }
    }

    @Throws(Exception::class)
    private suspend fun saveMovieWithProductionDetails(movieDetails: MovieDetailResponse) {
//        for(productionCompany in movieDetails.productionCompanyList) {
//            productionCompanyDao.saveProductionCompany(productionCompany
//                .toProductionCompanyDomainModel(false))
//
//            moviesWithProductionCompanyDao.saveMovieWithProductionCompany(
//                movieDetails.toMovieWithProductionCompany(productionCompany.id))
//        }
    }

    companion object {
        const val SUBSCRIPTION_TAG = "subscription_tag"
    }

    //TODO : Change subscription worker only to identify subscribed movies
}
