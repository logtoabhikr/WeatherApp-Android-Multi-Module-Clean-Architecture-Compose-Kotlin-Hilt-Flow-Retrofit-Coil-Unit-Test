package com.lbg.domain.usecase

import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Constants
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ForecastingUseCaseTest {
    @Mock
    lateinit var forecastingUseCase: ForecastingUseCase

    @Mock
    lateinit var lbgRepository: LBGRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastingUseCase = ForecastingUseCase(lbgRepository)
    }

    @Test
    operator fun invoke() {
        runTest {
            val result = forecastingUseCase.invoke(Constants.FORECASTING_DAYS)
            assertEquals(result, lbgRepository.getCurrentWeather())
        }
    }
}