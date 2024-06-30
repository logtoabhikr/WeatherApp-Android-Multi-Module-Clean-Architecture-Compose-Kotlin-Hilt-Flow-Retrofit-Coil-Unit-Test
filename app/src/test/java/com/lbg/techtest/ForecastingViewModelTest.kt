package com.lbg.techtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.lbg.data.utils.Resource
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.usecase.ForecastingUseCase
import com.lbg.techtest.presentation.viewmodel.ForecastingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ForecastingViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var useCase: ForecastingUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<Resource<WeatherEntity>>

    private lateinit var viewModel: ForecastingViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ForecastingViewModel(useCase)
    }

    @After
    fun tearDown() {
        viewModel.state.asLiveData().removeObserver { uiStateObserver }
    }

    @Test
    fun `do call return loading`() {
        coroutinesDispatcherRule.launch {
            viewModel.state.asLiveData().observeForever { uiStateObserver }
            viewModel.getForecastedWeather()
            Mockito.verify(uiStateObserver).onChanged(Resource.Loading())
        }
    }

    @Test
    fun `do call return error`() {
        val exception = Mockito.mock(HttpException::class.java)
        coroutinesDispatcherRule.launch {
            viewModel.state.asLiveData().observeForever { uiStateObserver }
            Mockito.`when`(useCase.getForecastingDays()).thenAnswer {
                Resource.Error("Error Occurred!", exception.message.toString())
            }
            viewModel.getForecastedWeather()
            assertNotNull(viewModel.state.value)
            assertEquals(Resource.Error("Error Occurred!", Any()), viewModel.state.value)
        }
    }

    @Test
    fun `do call return success`() {
        runTest {
            val data = Mockito.mock(WeatherEntity::class.java)
            coroutinesDispatcherRule.launch {
                viewModel.state.asLiveData().observeForever { uiStateObserver }
                Mockito.`when`(useCase.getForecastingDays()).thenAnswer {
                    Resource.Success(data)
                }
                viewModel.getForecastedWeather()
                assertNotNull(viewModel.state.value)
                assertEquals(Resource.Success(data), viewModel.state.value)
            }
        }
    }

    @Test
    fun `parseDateToDay() correctly parses a date string into a day name`() {
        coroutinesDispatcherRule.launch {
            val dateString = "2023-04-09"
            val expectedDayName = "Sun, 09 Apr"

            val actualDayName = viewModel.parseDateToDay(dateString)

            assertEquals(expectedDayName, actualDayName)
        }
    }

    @Test
    fun `parseDateToDay() returns the original date string if the date string is invalid`() {

        val dateString = "Invalid date string"

        val actualDayName = viewModel.parseDateToDay(dateString)

        assertEquals(dateString, actualDayName)
    }
}

