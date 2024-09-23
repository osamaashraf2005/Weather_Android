package com.forecast.weather.task.feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.forecast.weather.task.feature.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherDisplayScreen(
    cityName: String? = null,
    latitude: Double? = null,
    longitude: Double? = null,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weatherData by viewModel.weatherData.observeAsState()
    val error by viewModel.error.observeAsState()
    var isLoading by remember { mutableStateOf(true) }

    // Fetch weather data based on either city name or coordinates
    LaunchedEffect(cityName, latitude, longitude) {
        isLoading = true
        when {
            cityName != null -> viewModel.fetchWeatherByCity(cityName)
            latitude != null && longitude != null -> viewModel.fetchWeatherByLocation(latitude, longitude)
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally  // Center the icon and text
    ) {

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            // Display weather icon
            weatherData?.let { weather ->

                // Use Coil with caching policy
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = weather.icon)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                memoryCachePolicy(CachePolicy.ENABLED)
                                diskCachePolicy(CachePolicy.ENABLED)
                            }).build()
                    ),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(100.dp),  // Adjust size as needed
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("City: ${weather.name}", style = MaterialTheme.typography.titleSmall)
                Text("Description: ${weather.description}", style = MaterialTheme.typography.titleSmall)
                Text("Temperature: ${weather.temp}°C", style = MaterialTheme.typography.bodySmall)
                Text("Feels Like: ${weather.feelsLike}°C", style = MaterialTheme.typography.bodySmall)
                Text("Min Temp: ${weather.tempMin}°C", style = MaterialTheme.typography.bodySmall)
                Text("Max Temp: ${weather.tempMax}°C", style = MaterialTheme.typography.bodySmall)
                Text("Pressure: ${weather.pressure}°C", style = MaterialTheme.typography.bodySmall)
                Text("Humidity: ${weather.humidity}%", style = MaterialTheme.typography.bodySmall)
                Text("Visibility: ${weather.visibility/1000}Km", style = MaterialTheme.typography.bodySmall)
                Text("Wind Speed: ${weather.windSpeed}Km/h", style = MaterialTheme.typography.bodySmall)
            }

            // Handle errors
            error?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}