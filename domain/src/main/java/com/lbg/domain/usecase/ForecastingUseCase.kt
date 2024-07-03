package com.lbg.domain.usecase

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.ApiUseCaseParams
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastingUseCase @Inject constructor(private val repository: LBGRepository) :
    ApiUseCaseParams<ForecastingUseCase.Params, WeatherEntity> {

    data class Params(val days: String)

    override suspend fun execute(params: Params): Flow<Result<WeatherEntity>> {
        return repository.getForecasting(params.days)
    }
}