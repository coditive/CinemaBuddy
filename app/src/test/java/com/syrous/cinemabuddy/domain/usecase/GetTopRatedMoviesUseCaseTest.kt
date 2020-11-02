package com.syrous.cinemabuddy.domain.usecase


import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.DataConstant
import com.syrous.cinemabuddy.data.repository.MovieRepositoryImpl
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.DomainConstant
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import com.syrous.cinemabuddy.domain.repository.MovieRepositoryStub
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetTopRatedMoviesUseCaseTest {

    private val apiKey = "Sample_api_key"
    private val lang = "us-en"
    private val page = 1
    private val region = "sample_region"

    private lateinit var cut: GetTopRatedMoviesUseCase

    @Before
    fun setUp() {
        cut = GetTopRatedMoviesUseCase(MovieRepositoryStub())
    }


    @Test
    fun `Test TopRatedUseCase handles successful result of api call`() {
        //Given


        //when
        val result = runBlocking { cut.execute(apiKey, lang, page, region) }


        //then
        result shouldBeEqualTo GetTopRatedMoviesUseCase.UseState.Success(data = listOf(DomainConstant.getMovie()))
    }






}