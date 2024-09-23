package com.forecast.weather.task.di

import com.forecast.weather.task.feature.data.datasource.network.repository.WeatherRepositoryImpl
import com.forecast.weather.task.feature.data.datasource.network.service.WeatherService
import com.forecast.weather.task.feature.data.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideWeatherService(retrofit: Retrofit): WeatherService {
            return retrofit.create(WeatherService::class.java)
        }

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}
