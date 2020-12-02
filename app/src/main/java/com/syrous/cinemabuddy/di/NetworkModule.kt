package com.syrous.cinemabuddy.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.retrofit.moshiAdapters.DateJsonAdapterFactory
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
class NetworkModule {

    private val DEFAULT_TIMEOUT = 60L

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(DateJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): MoviesApi = Retrofit.Builder()
        .baseUrl(BuildConfig.REDIRECT_URI)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .client(okHttpClient)
        .build()
        .create(MoviesApi::class.java)

}