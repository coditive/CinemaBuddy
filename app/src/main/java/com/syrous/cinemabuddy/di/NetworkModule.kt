package com.syrous.cinemabuddy.di

import android.content.Context
import com.syrous.cinemabuddy.BuildConfig
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
    private val CACHE_SIZE = 10 * 1024 * 1024

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache) : OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }
            cache(cache)
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    internal fun provideCache(context: Context): Cache {
        return  Cache(context.cacheDir, CACHE_SIZE.toLong())
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): MoviesApi = Retrofit.Builder()
        .baseUrl(BuildConfig.REDIRECT_URI)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(MoviesApi::class.java)

}