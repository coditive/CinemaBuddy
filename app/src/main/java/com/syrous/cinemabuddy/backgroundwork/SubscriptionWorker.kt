package com.syrous.cinemabuddy.backgroundwork

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.R
import androidx.work.WorkerParameters
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.model.toChartedMovie
import com.syrous.cinemabuddy.data.retrofit.model.toMovieDbModel
import com.syrous.cinemabuddy.data.retrofit.model.toMovieWithGenre
import com.syrous.cinemabuddy.data.retrofit.model.toProductionCompanyDomainModel
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.UpcomingMovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.toMovieWithProductionCompany
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.utils.NOTIFICATION_CHANNEL_ID
import com.syrous.cinemabuddy.utils.SystemConfigStorage
import com.syrous.cinemabuddy.utils.createNotificationChannel
import kotlin.jvm.Throws
import kotlin.random.Random

class SubscriptionWorker(
    workerParameters: WorkerParameters,
    private val moviesApi: MoviesApi,
    private val chartedMoviesDao: ChartedMoviesDao,
    private val moviesWithGenreDao: MoviesWithGenreDao,
    private val moviesDao: MoviesDao,
    private val moviesWithProductionCompanyDao: MoviesWithProductionCompanyDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val systemConfigStorage: SystemConfigStorage,
    private val context: Context
    ): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
       return try {
           systemConfigStorage.updateSubscriptionWorkerSyncStartTime(System.currentTimeMillis())

           val result = moviesApi.getUpcomingMovies(BuildConfig.API_KEY_V3,
               systemConfigStorage.getUserLang(),1, null)

           saveMoviesToLocalStorage(result, ChartType.UPCOMING)

           for(movie in result.movieModelList) {
               val movieDetails = moviesApi.getMovieDetails(movie.id, BuildConfig.API_KEY_V3,
                   systemConfigStorage.getUserLang())

               saveMovieWithProductionDetails(movieDetails)
           }


           for(i in 2..result.totalPages) {
                val response = moviesApi.getUpcomingMovies(BuildConfig.API_KEY_V3,
                systemConfigStorage.getUserLang(), i, null)

               saveMoviesToLocalStorage(response, ChartType.UPCOMING)

               for(movie in response.movieModelList) {
                   val movieDetails = moviesApi.getMovieDetails(movie.id, BuildConfig.API_KEY_V3,
                       systemConfigStorage.getUserLang())

                   saveMovieWithProductionDetails(movieDetails)
               }
           }

           buildNotificationAndShow("Finished Subscription Saving in DB",
               "Upcoming Movies are stored in DB with production Companies", context)

           systemConfigStorage.updateSubscriptionWorkerSyncEndTime(System.currentTimeMillis())

           Result.success()
        } catch (e: Exception) {
            val failureOutput = Data.Builder()
                .putString(FAILURE_EXCEPTION, e.message)
                .build()

           buildNotificationAndShow("Error In Subscription Worker", e.message!!, context)

            Result.failure(failureOutput)
        }
    }

    private fun buildNotificationAndShow(title: String, message: String, context: Context, id: Int = Random.nextInt()) {
        context.createNotificationChannel()
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        NotificationManagerCompat.from(context).notify(id, builder.build())
    }


    @Throws(Exception::class)
    private suspend fun saveMoviesToLocalStorage(movieResponse: UpcomingMovieResponse, chartType: ChartType) {
        for (movie in movieResponse.movieModelList) {
            moviesDao.saveMovie(movie.toMovieDbModel())
            for(genre in movie.genreIdList) {
                moviesWithGenreDao.saveMovieWithGenre(movie.toMovieWithGenre(genre))
            }
            chartedMoviesDao.makeEntryForMovie(movie.toChartedMovie(chartType))
        }
    }

    @Throws(Exception::class)
    private suspend fun saveMovieWithProductionDetails(movieDetails: MovieDetailResponse) {
        for(productionCompany in movieDetails.productionCompanyList) {
            productionCompanyDao.saveProductionCompany(productionCompany
                .toProductionCompanyDomainModel(false))

            moviesWithProductionCompanyDao.saveMovieWithProductionCompany(
                movieDetails.toMovieWithProductionCompany(productionCompany.id))
        }
    }

    companion object {
        const val SUBSCRIPTION_TAG = "subscription_tag"
        const val FAILURE_EXCEPTION = "failure_exception"
    }
}
