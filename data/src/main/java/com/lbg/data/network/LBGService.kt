package com.lbg.data.network

import com.lbg.domain.entity.WeatherDto
import com.lbg.data.utils.BaseURL
import com.lbg.domain.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LBGService {

    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String = BaseURL.APIKEY,
        @Query("q") location: String = Constants.LOCATION_PARAM,
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
    ): Response<WeatherDto>
}