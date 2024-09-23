package com.forecast.weather.task.feature.data.model

import com.google.gson.annotations.SerializedName

// Added only the fields which are required to show the necessary weather details
// We can also name it as WeatherDto (as in NowInAndroid Architecture)
data class WeatherResponse(
    // Always a good option to add SerializedName, hence if any one rename the field
    // name in future, it will not break the code
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("name")
    val name: String
)

data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("sea_level")
    val seaLevel: Int?,
    // Spelling mistake in the API response so we corrected it in our bean,
    // another advantage of using SerializedName :)
    @SerializedName("grnd_level")
    val groundLevel: Int?
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double
)
