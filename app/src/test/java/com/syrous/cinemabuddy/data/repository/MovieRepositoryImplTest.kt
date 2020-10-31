package com.syrous.cinemabuddy.data.repository

import com.syrous.cinemabuddy.data.ConstantData
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.model.Result
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
    fun `fetchAndSaveTopRateMovies fetches top rated movies and convert it domain object list`() {
        // given
        coEvery {
            mockService.getTopRatedMoviesList(apiKey, lang, page, region)
        } returns ConstantData.getPopularMoviesList(apiKey, lang, page, region)

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
        result shouldBeEqualTo ConstantData.getPopularMoviesList(apiKey, lang, page, region).movieDomainModelList
    }
}