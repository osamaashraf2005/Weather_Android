package com.forecast.weather.task.feature.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forecast.weather.task.feature.domain.usecase.FetchWeatherUseCase
import com.forecast.weather.task.feature.presentation.mapper.WeatherMapper
import com.forecast.weather.task.feature.presentation.mapper.mapWith
import com.forecast.weather.task.feature.presentation.model.UiState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val weatherMapper: WeatherMapper,
    @ApplicationContext
    private val context: Context
): ViewModel() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    // We can also use StateFlow here, which also aware of the lifecycle, and have better performance.
    // but for simplicity I'm using liveData here
    private val _weatherData = MutableLiveData<UiState>()
    val weatherData: LiveData<UiState> get() = _weatherData

    // We can also use StateFlow here, which also aware of the lifecycle, and have better performance
    // but for simplicity I'm using liveData here
    private val _error = MutableLiveData<String>()

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> get() = _location

    val error: LiveData<String> get() = _error

    // FusedLocationProviderClient is Google's recommended way to get location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            try {
                _weatherData.value =
                    fetchWeatherUseCase
                        .getWeather(FetchWeatherUseCase.Query.ByCityName(city))
                        .mapWith(
                            mapper = weatherMapper
                        )
                saveLastSearchedCity(city)
            } catch (e: Exception) {
                _error.value = "Failed to fetch weather: ${e.message}"
            }
        }
    }

    fun fetchWeatherByLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val uiState = fetchWeatherUseCase
                    .getWeather(FetchWeatherUseCase.Query.ByCoordinates(latitude, longitude))
                    .mapWith(
                        mapper = weatherMapper
                    )
                _weatherData.value = uiState
                saveLastSearchedCity(uiState.name)

            } catch (e: Exception) {
                _error.value = "Failed to fetch weather: ${e.message}"
            }
        }
    }

    fun initializeFusedLocationClient(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getCurrentLocation(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    _location.postValue(location)
                }.addOnFailureListener {
                    _location.postValue(null)
                }
            } catch (e: SecurityException) {
                _location.postValue(null)
            }
        }
    }

    private fun saveLastSearchedCity(city: String) {
        sharedPreferences.edit().putString("last_city", city).apply()
    }

    fun getLastSearchedCity(): String? {
        return sharedPreferences.getString("last_city", null)
    }
}
