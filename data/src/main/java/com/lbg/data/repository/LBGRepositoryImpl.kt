package com.lbg.data.repository

import com.lbg.data.mapper.WeatherMapper
import com.lbg.data.network.LBGService
import com.lbg.data.utils.NetworkBoundResource
import com.lbg.data.utils.mapFrom
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LBGRepositoryImpl @Inject constructor(
    private val lbgService: LBGService,
    private val networkBoundResources: NetworkBoundResource,
    private val weatherMapper: WeatherMapper
) : LBGRepository {

    override suspend fun getCurrentWeather(): Flow<Result<WeatherEntity>> {
        return mapFrom(
            result = networkBoundResources.downloadData { lbgService.getCurrentWeather() },
            weatherMapper
        )
    }

    override suspend fun getForecasting(days: String): Flow<Result<WeatherEntity>> {
        return mapFrom(
            result = networkBoundResources.downloadData {
                lbgService.getCurrentWeather(days = days)
            }, weatherMapper
        )
    }
}