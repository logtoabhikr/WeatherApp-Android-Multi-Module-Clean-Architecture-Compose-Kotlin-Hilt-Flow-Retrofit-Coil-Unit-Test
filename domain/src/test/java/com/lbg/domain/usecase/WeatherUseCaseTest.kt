package com.lbg.domain.usecase

import com.google.common.truth.Truth
import com.lbg.domain.mapper.WeatherMapper
import com.lbg.domain.model.WeatherModel
import com.lbg.domain.provideWeatherDto
import com.lbg.domain.provideWeatherModel
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class WeatherUseCaseTest {

    @Mock
    private lateinit var lbgRepository: LBGRepository

    @Mock
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var weatherUseCase: WeatherUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherUseCase = WeatherUseCase(lbgRepository, weatherMapper)
    }

    @Test
    fun `invoke should return success`() = runTest {
        val weatherDto = provideWeatherDto()
        val weatherModel = provideWeatherModel()

        `when`(lbgRepository.getCurrentWeather()).thenReturn(flow { emit(Result.Success(weatherDto)) })
        Mockito.`when`(weatherMapper.mapFrom(weatherDto)).thenReturn(weatherModel)

        val state = MutableStateFlow<Result<WeatherModel>>(Result.Success(weatherModel))
        weatherUseCase.invoke().collect()
        {
            state.value = it
        }
        Truth.assertThat(state.value is Result.Success)
    }

    @Test
    fun `invoke should return error`() = runTest {

        val errorResponse = Mockito.mock(HttpException::class.java)
        `when`(lbgRepository.getCurrentWeather()).thenReturn(flow {
            emit(
                Result.Error(
                    Constants.UNKNOWN_ERROR,
                    errorResponse.code()
                )
            )
        })

        val state = MutableStateFlow<Result<WeatherModel>>(
            Result.Error(
                Constants.UNKNOWN_ERROR,
                errorResponse.code()
            )
        )
        weatherUseCase.invoke().collect()
        {
            state.value = it
        }

        Truth.assertThat(state.value is Result.Error).isTrue()
        Truth.assertThat(errorResponse.code()).isEqualTo((state.value as Result.Error).code)
        Truth.assertThat(Constants.UNKNOWN_ERROR).isEqualTo((state.value as Result.Error).message)
    }
}