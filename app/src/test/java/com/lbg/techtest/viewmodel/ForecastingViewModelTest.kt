package com.lbg.techtest.viewmodel

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.usecase.ForecastingUseCase
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import com.lbg.techtest.TestDispatcherProvider
import com.lbg.techtest.presentation.viewmodel.ForecastingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ForecastingViewModelTest {

    @Mock
    private lateinit var repository: LBGRepository

    @Mock
    private lateinit var useCase: ForecastingUseCase

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: ForecastingViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dispatcherProvider = TestDispatcherProvider()
        useCase = ForecastingUseCase(repository)
        viewModel = ForecastingViewModel(useCase, dispatcherProvider)
    }


    @Test
    fun `do call return loading`() {
        runTest {

            Mockito.`when`(useCase.invoke(Constants.FORECASTING_DAYS)).thenReturn(
                flow { emit(Result.Loading) }
            )

            viewModel.getForecastedWeather()

            assertTrue(viewModel.state.value is Resource.Loading)
        }
    }

    @Test
    fun `do call return error`() {
        runTest {
            Mockito.`when`(useCase.invoke(Constants.FORECASTING_DAYS)).thenReturn(
                flow { emit(Result.Error("", 0)) }
            )

            viewModel.getForecastedWeather()

            assertTrue(viewModel.state.value is Resource.Error)
        }
    }

    @Test
    fun `do call return exception`() {
        val exception = Mockito.mock(HttpException::class.java)
        runTest {
            Mockito.`when`(useCase.invoke(Constants.FORECASTING_DAYS)).thenReturn(
                flow { emit(Result.Error(exception.message(), exception.code())) }
            )

            viewModel.getForecastedWeather()

            assertTrue(viewModel.state.value is Resource.Error)
        }
    }

    @Test
    fun `do call return success`() {
        val mockResponse = Mockito.mock(WeatherEntity::class.java)
        runTest {
            Mockito.`when`(useCase.invoke(Constants.FORECASTING_DAYS)).thenReturn(
                flow { emit(Result.Success(mockResponse)) }
            )

            viewModel.getForecastedWeather()

            assertTrue(viewModel.state.value is Resource.Success)
        }
    }
}

