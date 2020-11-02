package com.syrous.cinemabuddy.domain.model

import com.syrous.cinemabuddy.data.DataConstant
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MovieDomainModelTest {

    private lateinit var movieDomainModel: MovieDomainModel

    @Before
    fun `set up movies object to test`(){
      movieDomainModel = DataConstant.getMovie()
    }

    @Test
    fun `Test data-type of all fields of Movie data class`() {
        assertEquals(true, movieDomainModel.id is Int)
        assertEquals(true, movieDomainModel.title is String)
        assertEquals(true, movieDomainModel.originalTitle is String)
        assertEquals(true, movieDomainModel.overview is String?)
        assertEquals(true, movieDomainModel.isAdult is Boolean)
        assertEquals(true, movieDomainModel.releaseDate is String)
        assertEquals(true, movieDomainModel.posterPath is String?)
        assertEquals(true, movieDomainModel.backdropPath is String?)
        assertEquals(true, movieDomainModel.originalLang is String)
        assertEquals(true, movieDomainModel.genreIdList is List<Int>)
        assertEquals(true, movieDomainModel.video is Boolean)
        assertEquals(true, movieDomainModel.voteAverage is Double)
        assertEquals(true, movieDomainModel.popularity is Double)
        assertEquals(true, movieDomainModel.voteCount is Int)
    }
}