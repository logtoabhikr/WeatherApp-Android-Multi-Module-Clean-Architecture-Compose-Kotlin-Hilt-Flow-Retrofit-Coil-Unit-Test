package com.lbg.domain.usecase

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val lbgRepository: LBGRepository) {
    suspend operator fun invoke(): Flow<Result<WeatherEntity>> = lbgRepository.getCurrentWeather()
}