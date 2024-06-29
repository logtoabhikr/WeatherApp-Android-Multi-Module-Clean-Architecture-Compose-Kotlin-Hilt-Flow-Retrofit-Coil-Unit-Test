package com.lbg.domain.entity

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    val location: Location? = null,
    val current: Current? = null,
    val forecast: Forecast? = null
)

data class Current(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long? = null,

    @SerializedName("last_updated")
    val lastUpdated: String? = null,

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("temp_f")
    val tempF: Double,

    @SerializedName("is_day")
    val isDay: Long,

    val condition: Condition,

    @SerializedName("wind_mph")
    val windMph: Double,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("wind_degree")
    val windDegree: Long,

    @SerializedName("wind_dir")
    val windDir: String,

    @SerializedName("pressure_mb")
    val pressureMB: Long,

    @SerializedName("pressure_in")
    val pressureIn: Double,

    @SerializedName("precip_mm")
    val precipMm: Double,

    @SerializedName("precip_in")
    val precipIn: Double,

    val humidity: Long,
    val cloud: Long,

    @SerializedName("feelslike_c")
    val feelslikeC: Double,

    @SerializedName("feelslike_f")
    val feelslikeF: Double,

    @SerializedName("windchill_c")
    val windchillC: Double,

    @SerializedName("windchill_f")
    val windchillF: Double,

    @SerializedName("heatindex_c")
    val heatindexC: Double,

    @SerializedName("heatindex_f")
    val heatindexF: Double,

    @SerializedName("dewpoint_c")
    val dewpointC: Double,

    @SerializedName("dewpoint_f")
    val dewpointF: Double,

    @SerializedName("vis_km")
    val visKM: Long,

    @SerializedName("vis_miles")
    val visMiles: Long,

    val uv: Long,

    @SerializedName("gust_mph")
    val gustMph: Double,

    @SerializedName("gust_kph")
    val gustKph: Double,

    @SerializedName("time_epoch")
    val timeEpoch: Long? = null,

    val time: String? = null,

    @SerializedName("snow_cm")
    val snowCM: Long? = null,

    @SerializedName("will_it_rain")
    val willItRain: Long? = null,

    @SerializedName("chance_of_rain")
    val chanceOfRain: Long? = null,

    @SerializedName("will_it_snow")
    val willItSnow: Long? = null,

    @SerializedName("chance_of_snow")
    val chanceOfSnow: Long? = null
)


data class Condition(
    val text: String,
    val icon: String,
    val code: Long
)


data class Forecast(
    val forecastday: List<Forecastday>
)


data class Forecastday(
    val date: String,

    @SerializedName("date_epoch")
    val dateEpoch: Long,

    val day: Day,
    val astro: Astro,
    val hour: List<Current>
)


data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,

    @SerializedName("moon_phase")
    val moonPhase: String,

    @SerializedName("moon_illumination")
    val moonIllumination: Long,

    @SerializedName("is_moon_up")
    val isMoonUp: Long,

    @SerializedName("is_sun_up")
    val isSunUp: Long
)


data class Day(
    @SerializedName("maxtemp_c")
    val maxtempC: Double? = null,

    @SerializedName("maxtemp_f")
    val maxtempF: Double,

    @SerializedName("mintemp_c")
    val mintempC: Double? = null,

    @SerializedName("mintemp_f")
    val mintempF: Double,

    @SerializedName("avgtemp_c")
    val avgtempC: Double,

    @SerializedName("avgtemp_f")
    val avgtempF: Double,

    @SerializedName("maxwind_mph")
    val maxwindMph: Double,

    @SerializedName("maxwind_kph")
    val maxwindKph: Double,

    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double,

    @SerializedName("totalprecip_in")
    val totalprecipIn: Double,

    @SerializedName("totalsnow_cm")
    val totalsnowCM: Long,

    @SerializedName("avgvis_km")
    val avgvisKM: Double,

    @SerializedName("avgvis_miles")
    val avgvisMiles: Long,

    val avghumidity: Long,

    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Long,

    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Long,

    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Long,

    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Long,

    val condition: Condition,
    val uv: Long
)


data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,

    @SerializedName("tz_id")
    val tzID: String,

    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,

    val localtime: String
)