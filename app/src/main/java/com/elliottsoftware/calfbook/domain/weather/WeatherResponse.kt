package com.elliottsoftware.calfbook.domain.weather

sealed class WeatherResponse<out T> {
    object Loading: WeatherResponse<Nothing>()

    data class Success<out T>(
        val data:T
    ):WeatherResponse<T>()

    data class Failure<out T>(
        val e: Exception
    ): WeatherResponse<Nothing>()
}