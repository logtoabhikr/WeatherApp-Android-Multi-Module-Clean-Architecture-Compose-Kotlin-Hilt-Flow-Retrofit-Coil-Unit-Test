package com.lbg.domain.model

data class WeatherModel(
    val location: LocationModel? = null,
    val current: CurrentModel? = null,
    val forecast: ForecastModel? = null
)

data class CurrentModel(
    val tempC: Double,
    val condition: ConditionModel,
    val humidity: Long,
    val cloud: Long,
    val feelslikeC: Double,
    val uv: Long,
    val time: String? = null,
)


data class ConditionModel(
    val text: String,
    val icon: String,
)


data class ForecastModel(
    val forecastday: List<ForecastdayModel>
)


data class ForecastdayModel(
    val date: String,
    val day: DayModel,
    val hour: List<CurrentModel>
)


data class DayModel(
    val maxtempC: Double? = null,
    val mintempC: Double? = null,
    val condition: ConditionModel,
)


data class LocationModel(
    val name: String,
    val country: String,
    val localtime: String
)