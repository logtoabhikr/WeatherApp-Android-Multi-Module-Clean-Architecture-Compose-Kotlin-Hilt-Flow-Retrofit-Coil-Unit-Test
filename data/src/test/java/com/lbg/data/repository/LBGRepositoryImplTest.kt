package com.lbg.data.repository

import com.lbg.data.mapper.WeatherMapper
import com.lbg.data.model.WeatherDto
import com.lbg.data.network.LBGService
import com.lbg.data.utils.NetworkBoundResource
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.utils.Result
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LBGRepositoryImplTest {

    @Mock
    lateinit var lbgRepositoryImpl: LBGRepositoryImpl

    @Mock
    lateinit var lbgService: LBGService

    @Mock
    lateinit var networkBoundResource: NetworkBoundResource

    @Mock
    lateinit var weatherMapper: WeatherMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        lbgRepositoryImpl = LBGRepositoryImpl(lbgService, networkBoundResource, weatherMapper)
    }



    @Test
    fun getWeather() {
        runTest {
            val data = Mockito.mock(WeatherDto::class.java)
            val weatherEntity = Mockito.mock(WeatherEntity::class.java)
            Mockito.`when`(networkBoundResource.downloadData { lbgService.getCurrentWeather() })
                .thenAnswer { Result.Success(data) }
            Mockito.`when`(weatherMapper.mapFrom(data)).thenAnswer {
                Result.Success(weatherEntity)
            }
        }
    }
    @After
    fun tearDown() {

    }
}