package com.lbg.data.repository

import com.google.common.truth.Truth
import com.lbg.data.TestDispatcherProvider
import com.lbg.data.mapper.WeatherMapper
import com.lbg.data.network.LBGService
import com.lbg.data.utils.NetworkBoundResource
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LBGRepositoryImplTest {

    @Mock
    private lateinit var lbgService: LBGService

    @Mock
    private lateinit var networkBoundResources: NetworkBoundResource

    @Mock
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var sut: LBGRepositoryImpl
    private lateinit var dispatcherProvider: DispatcherProvider


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcherProvider = TestDispatcherProvider()
        networkBoundResources = NetworkBoundResource(dispatcherProvider)
        sut = LBGRepositoryImpl(lbgService, networkBoundResources, weatherMapper)
    }


    @Test
    fun getCurrentWeather() {
        runTest {
            val weatherEntity = Mockito.mock(WeatherEntity::class.java)

            Mockito.`when`(lbgService.getCurrentWeather())
                .thenAnswer { Result.Success(weatherEntity) }
            /*Mockito.`when`(weatherMapper.mapFrom(data)).thenAnswer {
                flow { emit(Result.Success(weatherEntity)) }
            }*/

            val state = MutableStateFlow<Result<WeatherEntity>>(Result.Success(weatherEntity))
            sut.getCurrentWeather().collect {
                state.value = it
            }

            Truth.assertThat(state.value is Result.Success)
        }
    }

    @Test
    fun getForecasting() {
        runTest {
            val weatherEntity = Mockito.mock(WeatherEntity::class.java)

            Mockito.`when`(lbgService.getCurrentWeather())
                .thenAnswer { Result.Success(weatherEntity) }

            val state = MutableStateFlow<Result<WeatherEntity>>(Result.Success(weatherEntity))
            sut.getForecasting(Constants.FORECASTING_DAYS).collect {
                state.value = it
            }

            Truth.assertThat(state.value is Result.Success)
        }
    }
}