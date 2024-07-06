package com.lbg.domain.usecase

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastingUseCase @Inject constructor(
    private val repository: LBGRepository,
) {
    suspend operator fun invoke(days: String): Flow<Result<WeatherEntity>> =
        repository.getForecasting(days = days)
}