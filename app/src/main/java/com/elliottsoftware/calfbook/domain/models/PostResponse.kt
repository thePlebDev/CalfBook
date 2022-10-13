package com.elliottsoftware.calfbook.domain.models

sealed class PostResponse<out T> {
    object Loading: PostResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): PostResponse<T>()

    data class Failure(
        val e: String
    ): PostResponse<Nothing>()
}
