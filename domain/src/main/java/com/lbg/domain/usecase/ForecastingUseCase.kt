package com.lbg.domain.usecase

import com.lbg.domain.repository.LBGRepository
import javax.inject.Inject

class ForecastingUseCase @Inject constructor(private val repository: LBGRepository) {

    suspend fun getForecastingDays() = repository.getForecasting("10")

}