package com.syrous.cinemabuddy.data.usecases

import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.local.dao.ChartedMoviesDao
import com.syrous.cinemabuddy.data.local.dao.MoviesDao
import com.syrous.cinemabuddy.data.local.dao.MoviesWithGenreDao
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import kotlin.jvm.Throws

@FlowPreview
@RunWith(MockitoJUnitRunner::class)
class FetchChartedMoviesUseCaseImplTest {

    //region constant  -----------------------------------------------------------------------------
    private val DEFAULT_LANGUAGE = "default_language"

    //endregion constant ---------------------------------------------------------------------------

    //region helper fields -------------------------------------------------------------------------

    @Mock
    lateinit var moviesDao: MoviesDao

    @Mock
    lateinit var moviesApi: MoviesApi

    @Mock
    lateinit var moviesWithGenreDao: MoviesWithGenreDao

    @Mock
    lateinit var chartedMoviesDao: ChartedMoviesDao

    lateinit var systemConfigUseCase: SystemConfigUseCase
    //endregion helper fields ----------------------------------------------------------------------

    private lateinit var SUT: FetchChartedMoviesUseCaseImpl

    @Throws(Exception::class)
    @Before
    fun setup() {


    }

    private fun success() {

    }

    //fetch Top-Rated movies from network -> Success
    @Test
    fun executeUseCase_fetchAndSaveTopRatedMovies_successReturned() {
    //Given
        val ac = ArgumentCaptor.forClass(String::class.java)
        val acInt = ArgumentCaptor.forClass(Int::class.java)
    //when
        runBlocking {
            SUT.execute()
        }
    //then
        runBlocking {
            verify(moviesApi).getTopRatedMoviesList(ac.capture(), ac.capture(),
                acInt.capture(), ac.capture())
        }
        assertThat(ac.allValues[0], `is`(BuildConfig.API_KEY_V3))
    }

//fetch Top-Rated movies from network -> NetworkError
    //fetch Top-Rated movies from network -> GeneralError
    //Save  Top-Rated movies to local Storage -> Success
    //Save  Top-Rated movies to local Storage -> Failure

    //fetch Popular movies from network -> Success
    //fetch Popular movies from network -> NetworkError
    //fetch Popular movies from network -> GeneralError
    //Save  Popular movies to local Storage -> Success
    //Save  Popular movies to local Storage -> Failure

    //fetch Upcoming movies from network -> Success
    //fetch Upcoming movies from network -> NetworkError
    //fetch Upcoming movies from network -> GeneralError
    //Save  Upcoming movies to local Storage -> Success
    //Save  Upcoming movies to local Storage -> Failure

    //region helper methods ------------------------------------------------------------------------

    //endregion helper methods ---------------------------------------------------------------------

    //region helper classes ------------------------------------------------------------------------


    //endregion helper classes ---------------------------------------------------------------------
}