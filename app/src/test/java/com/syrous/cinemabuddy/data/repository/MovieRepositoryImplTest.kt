package com.syrous.cinemabuddy.data.repository

import com.syrous.cinemabuddy.data.DataConstant
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.Result.Success
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    @MockK
    private lateinit var mockService: MoviesApi

    private lateinit var cut: MovieRepositoryImpl

    private val apiKey = "Sample_Api_Key"
    private val lang = "us-en"
    private val page = 1
    private val region = "sample_region"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cut = MovieRepositoryImpl(mockService)
    }


    @Test
    fun `fetchAndSaveTopRatedMovies fetches top rated movies and convert it to list of domain object`() {
        // given
        coEvery {
            mockService.getTopRatedMoviesList(apiKey, lang, page, region)
        } returns DataConstant.getTopRatedMoviesList(apiKey, lang, page, region)

        // when
        val result = runBlocking {
           val res = cut.fetchAndCacheTopRateMovies(apiKey, lang, page, region)
            if(res is Success) {
               res.data
           }else {
               emptyList()
           }
        }

        // then
        result shouldBeEqualTo DataConstant.getTopRatedMoviesList(apiKey, lang, page, region).movieDomainModelList
    }

    @Test
    fun `fetchAndSavePopularMovies fetches popular movies and convert it to list of domain object`() {
        // given
        coEvery {
            mockService.getPopularMoviesList(apiKey, lang, page, region)
        } returns DataConstant.getPopularMoviesList(apiKey, lang, page, region)

        // when
        val result = runBlocking {
            val res = cut.fetchAndCachePopularMovies(apiKey, lang, page, region)
            if(res is Success) {
                res.data
            }else {
                emptyList()
            }
        }

        // then
        result shouldBeEqualTo DataConstant.getPopularMoviesList(apiKey, lang, page, region).movieDomainModelList
    }





}