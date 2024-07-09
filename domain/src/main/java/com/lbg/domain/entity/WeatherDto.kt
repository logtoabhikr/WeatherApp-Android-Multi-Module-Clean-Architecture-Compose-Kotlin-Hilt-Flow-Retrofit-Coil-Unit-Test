package com.lbg.domain.entity

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    val location: Location? = null,
    val current: Current? = null,
    val forecast: Forecast? = null
)

data class Current(
    @SerializedName("temp_c")
    val tempC: Double,
    val condition: Condition,
    val humidity: Long,
    val cloud: Long,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    val uv: Long,
    val time: String? = null,
)


data class Condition(
    val text: String,
    val icon: String,
)


data class Forecast(
    val forecastday: List<Forecastday>
)


data class Forecastday(
    val date: String,
    val day: Day,
    val hour: List<Current>
)


data class Day(
    @SerializedName("maxtemp_c")
    val maxtempC: Double? = null,
    @SerializedName("mintemp_c")
    val mintempC: Double? = null,
    val condition: Condition,
)


data class Location(
    val name: String,
    val country: String,
    val localtime: String
)
