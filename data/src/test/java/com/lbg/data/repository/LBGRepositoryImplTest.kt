package com.lbg.data.repository

import com.google.common.truth.Truth
import com.lbg.data.TestDispatcherProvider
import com.lbg.data.network.LBGService
import com.lbg.data.utils.NetworkBoundResource
import com.lbg.domain.entity.WeatherDto
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LBGRepositoryImplTest {
    @Mock
    private lateinit var lbgService: LBGService

    @Mock
    private lateinit var networkBoundResources: NetworkBoundResource
    private lateinit var sut: LBGRepositoryImpl
    private lateinit var dispatcherProvider: DispatcherProvider
    private val weatherDto = Mockito.mock(WeatherDto::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcherProvider = TestDispatcherProvider()
        networkBoundResources = NetworkBoundResource(dispatcherProvider)
        sut = LBGRepositoryImpl(lbgService, networkBoundResources)
    }

    @Test
    fun getCurrentWeather() {
        runTest {
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenAnswer { Response.success(weatherDto) }
            val state = MutableStateFlow<Result<WeatherDto>>(Result.Success(weatherDto))
            sut.getCurrentWeather().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Success)
        }
    }

    @Test
    fun `getCurrentWeather return error`() {
        runTest {
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenAnswer { Result.Error<WeatherDto>(message = "", code = 0) }

            val state = MutableStateFlow<Result<WeatherDto>>(Result.Error("", 0))
            sut.getCurrentWeather().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getCurrentWeather return unknown error`() {
        runTest {
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenReturn(Response.error(500, "".toResponseBody()))

            val state =
                MutableStateFlow<Result<WeatherDto>>(Result.Error(Constants.UNKNOWN_ERROR, 500))
            sut.getCurrentWeather().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getCurrentWeather return http exception`() {
        runTest {
            val httpException = Mockito.mock(HttpException::class.java)
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenThrow(httpException)
            val state =
                MutableStateFlow<Result<WeatherDto>>(
                    Result.Error(
                        Constants.SOMETHING_ERROR,
                        httpException.code()
                    )
                )
            sut.getCurrentWeather().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun getForecasting() {
        runTest {
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenAnswer { Result.Success(weatherDto) }

            val state = MutableStateFlow<Result<WeatherDto>>(Result.Success(weatherDto))
            sut.getForecasting(Constants.FORECASTING_DAYS).collect {
                state.value = it
            }

            Truth.assertThat(state.value is Result.Success)
        }
    }

    @Test
    fun `getForecasting return error`() {
        runTest {
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenReturn(Response.error(400, Constants.UNKNOWN_ERROR.toResponseBody()))

            val state = MutableStateFlow<Result<WeatherDto>>(
                Result.Error(
                    message = Constants.UNKNOWN_ERROR,
                    code = 400
                )
            )
            sut.getForecasting(any()).collect {
                state.value = it
            }

            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getForecasting return unknown error`() {
        runTest {
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenReturn(Response.error(500, "".toResponseBody()))

            val state =
                MutableStateFlow<Result<WeatherDto>>(Result.Error(Constants.UNKNOWN_ERROR, 500))
            sut.getForecasting(any()).collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getForecasting return socket exception`() {
        runTest {
            val exception = Mockito.mock(HttpException::class.java)
            Mockito.`when`(lbgService.getCurrentWeather())
                .thenThrow(exception)
            val state =
                MutableStateFlow<Result<WeatherDto>>(
                    Result.Error(
                        Constants.UNKNOWN_ERROR,
                        0
                    )
                )
            sut.getForecasting(any()).collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }
}