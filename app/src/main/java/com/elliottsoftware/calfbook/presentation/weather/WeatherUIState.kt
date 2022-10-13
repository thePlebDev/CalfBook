package com.elliottsoftware.calfbook.presentation.weather

import com.elliottsoftware.calfbook.data.remote.Post

data class WeatherUIState(
    val isLoading:Boolean = true,
    val postList:List<Post>? = null
)