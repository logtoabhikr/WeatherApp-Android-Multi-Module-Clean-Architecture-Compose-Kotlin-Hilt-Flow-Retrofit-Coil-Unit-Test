package com.lbg.domain.usecase

import com.google.common.truth.Truth
import com.lbg.domain.mapper.WeatherMapper
import com.lbg.domain.model.WeatherModel
import com.lbg.domain.provideWeatherDto
import com.lbg.domain.provideWeatherModel
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.Result
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
class ForecastingUseCaseTest {
    @Mock
    private lateinit var lbgRepository: LBGRepository

    @Mock
    private lateinit var weatherMapper: WeatherMapper
    private lateinit var forecastingUseCase: ForecastingUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastingUseCase = ForecastingUseCase(lbgRepository, weatherMapper)
    }

    @Test
    fun `invoke should return success`() = runTest {
        val weatherDto = provideWeatherDto()
        val weatherModel = provideWeatherModel()

        `when`(lbgRepository.getForecasting(Constants.FORECASTING_DAYS)).thenReturn(flow {
            emit(
                Result.Success(weatherDto)
            )
        })
        `when`(weatherMapper.mapFrom(weatherDto)).thenReturn(weatherModel)

        val state = MutableStateFlow<Result<WeatherModel>>(Result.Success(weatherModel))
        forecastingUseCase.invoke(Constants.FORECASTING_DAYS).collect()
        {
            state.value = it
        }
        Truth.assertThat(state.value is Result.Success)
    }

    @Test
    fun `invoke should return error`() = runTest {

        val errorResponse = Mockito.mock(HttpException::class.java)
        `when`(lbgRepository.getForecasting(Constants.FORECASTING_DAYS)).thenReturn(flow {
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
        forecastingUseCase.invoke(Constants.FORECASTING_DAYS).collect()
        {
            state.value = it
        }

        Truth.assertThat(state.value is Result.Error).isTrue()
        Truth.assertThat(errorResponse.code()).isEqualTo((state.value as Result.Error).code)
        Truth.assertThat(Constants.UNKNOWN_ERROR).isEqualTo((state.value as Result.Error).message)
    }
}