package com.elliottsoftware.calfbook.domain.models

sealed class Response2<out T> {
    object Loading: Response2<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response2<T>()

    data class Failure(
        val e: Exception
    ): Response2<Nothing>()
}