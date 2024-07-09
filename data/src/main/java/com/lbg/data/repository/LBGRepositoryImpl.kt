package com.lbg.data.repository

import com.lbg.data.network.LBGService
import com.lbg.data.utils.NetworkBoundResource
import com.lbg.domain.entity.WeatherDto
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LBGRepositoryImpl @Inject constructor(
    private val lbgService: LBGService,
    private val networkBoundResources: NetworkBoundResource,
) : LBGRepository {

    override suspend fun getCurrentWeather(): Flow<Result<WeatherDto>> {
        return networkBoundResources.downloadData { lbgService.getCurrentWeather() }
    }

    override suspend fun getForecasting(days: String): Flow<Result<WeatherDto>> {
        return networkBoundResources.downloadData {
            lbgService.getCurrentWeather(days = days)
        }
    }
}