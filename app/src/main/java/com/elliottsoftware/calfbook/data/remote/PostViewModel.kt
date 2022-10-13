package com.elliottsoftware.calfbook.data.remote

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliottsoftware.calfbook.domain.models.PostResponse
import com.elliottsoftware.calfbook.domain.models.Response2
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elliottsoftware.calfbook.domain.use_cases.GetPostsUseCase
import com.elliottsoftware.calfbook.presentation.login.LoginFormState
import com.elliottsoftware.calfbook.presentation.weather.WeatherUIState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response


class PostViewModel(
    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase()
):ViewModel() {
    var state by mutableStateOf(WeatherUIState())

    var loadingResponse by mutableStateOf<PostResponse<Boolean>>(PostResponse.Loading)
    private set



    fun getPosts() = viewModelScope.launch {
        val posts = getPostsUseCase()
        state = state.copy(
            isLoading = false,
            postList = posts

        )


    }

    private fun updateLoadingState(loadingState:Boolean){
        state = state.copy(isLoading = loadingState)

    }


}
