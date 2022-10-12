package com.elliottsoftware.calfbook.domain.models

sealed class TestingResponse<out T> {
    object Loading: TestingResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): TestingResponse<T>()

    data class Failure(
        val e: Exception
    ): TestingResponse<Nothing>()
}