package com.lbg.domain.usecase

import com.lbg.domain.mapper.WeatherMapper
import com.lbg.domain.model.WeatherModel
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Result
import com.lbg.domain.utils.mapFrom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val lbgRepository: LBGRepository,
    private val weatherMapper: WeatherMapper
) {
    suspend operator fun invoke(): Flow<Result<WeatherModel>> {
        return mapFrom(lbgRepository.getCurrentWeather(), weatherMapper)
    }
}