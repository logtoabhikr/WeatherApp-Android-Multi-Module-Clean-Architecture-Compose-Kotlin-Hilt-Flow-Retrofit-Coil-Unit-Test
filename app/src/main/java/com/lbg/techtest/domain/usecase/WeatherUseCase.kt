package com.lbg.techtest.domain.usecase

import com.lbg.techtest.data.entity.WeatherEntity
import com.lbg.techtest.domain.repository.LBGRepository
import retrofit2.Response
import javax.inject.Inject

/*class CurrentWeatherUseCase @Inject constructor(
    private val LBGRepository: LBGRepository
):ApiUseCaseParams<CurrentWeatherUseCase.Params,ProductDetailsDto>{
    override suspend fun execute(params: Params): Flow<Result<ProductDetailsDto>> {
        return LBGRepository.getProductDetails()
    }
    data class Params(val userName:String)
}*/

class WeatherUseCase @Inject constructor(private val lbgRepository: LBGRepository) {
    suspend fun getCurrentWeather(): Response<WeatherEntity> = lbgRepository.getCurrentWeather()
}