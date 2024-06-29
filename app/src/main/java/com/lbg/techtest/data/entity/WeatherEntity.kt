package com.lbg.techtest.data.entity

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
    val tempC: Double? = null,

    @SerializedName("temp_f")
    val tempF: Double? = null,

    @SerializedName("is_day")
    val isDay: Long? = null,

    val condition: Condition? = null,

    @SerializedName("wind_mph")
    val windMph: Double? = null,

    @SerializedName("wind_kph")
    val windKph: Double? = null,

    @SerializedName("wind_degree")
    val windDegree: Long? = null,

    @SerializedName("wind_dir")
    val windDir: String? = null,

    @SerializedName("pressure_mb")
    val pressureMB: Long? = null,

    @SerializedName("pressure_in")
    val pressureIn: Double? = null,

    @SerializedName("precip_mm")
    val precipMm: Long? = null,

    @SerializedName("precip_in")
    val precipIn: Long? = null,

    val humidity: Long? = null,
    val cloud: Long? = null,

    @SerializedName("feelslike_c")
    val feelslikeC: Double? = null,

    @SerializedName("feelslike_f")
    val feelslikeF: Double? = null,

    @SerializedName("windchill_c")
    val windchillC: Double? = null,

    @SerializedName("windchill_f")
    val windchillF: Double? = null,

    @SerializedName("heatindex_c")
    val heatindexC: Double? = null,

    @SerializedName("heatindex_f")
    val heatindexF: Double? = null,

    @SerializedName("dewpoint_c")
    val dewpointC: Double? = null,

    @SerializedName("dewpoint_f")
    val dewpointF: Double? = null,

    @SerializedName("vis_km")
    val visKM: Long? = null,

    @SerializedName("vis_miles")
    val visMiles: Long? = null,

    val uv: Long? = null,

    @SerializedName("gust_mph")
    val gustMph: Double? = null,

    @SerializedName("gust_kph")
    val gustKph: Double? = null,

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
    val text: String? = null,
    val icon: String? = null,
    val code: Long? = null
)


data class Forecast(
    val forecastday: List<Forecastday>? = null
)

data class Forecastday(
    val date: String? = null,

    @SerializedName("date_epoch")
    val dateEpoch: Long? = null,

    val day: Day? = null,
    val astro: Astro? = null,
    val hour: List<Current>? = null
)

data class Astro(
    val sunrise: String? = null,
    val sunset: String? = null,
    val moonrise: String? = null,
    val moonset: String? = null,

    @SerializedName("moon_phase")
    val moonPhase: String? = null,

    @SerializedName("moon_illumination")
    val moonIllumination: Long? = null,

    @SerializedName("is_moon_up")
    val isMoonUp: Long? = null,

    @SerializedName("is_sun_up")
    val isSunUp: Long? = null
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxtempC: Double? = null,

    @SerializedName("maxtemp_f")
    val maxtempF: Double? = null,

    @SerializedName("mintemp_c")
    val mintempC: Double? = null,

    @SerializedName("mintemp_f")
    val mintempF: Double? = null,

    @SerializedName("avgtemp_c")
    val avgtempC: Double? = null,

    @SerializedName("avgtemp_f")
    val avgtempF: Double? = null,

    @SerializedName("maxwind_mph")
    val maxwindMph: Double? = null,

    @SerializedName("maxwind_kph")
    val maxwindKph: Double? = null,

    @SerializedName("totalprecip_mm")
    val totalprecipMm: Long? = null,

    @SerializedName("totalprecip_in")
    val totalprecipIn: Long? = null,

    @SerializedName("totalsnow_cm")
    val totalsnowCM: Long? = null,

    @SerializedName("avgvis_km")
    val avgvisKM: Long? = null,

    @SerializedName("avgvis_miles")
    val avgvisMiles: Long? = null,

    val avghumidity: Long? = null,

    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Long? = null,

    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Long? = null,

    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Long? = null,

    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Long? = null,

    val condition: Condition? = null,
    val uv: Long? = null
)

data class Location(
    val name: String? = null,
    val region: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,

    @SerializedName("tz_id")
    val tzID: String? = null,

    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long? = null,

    val localtime: String? = null
)