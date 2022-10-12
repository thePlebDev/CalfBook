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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response


class PostViewModel:ViewModel() {

    var loadingResponse by mutableStateOf<PostResponse<Boolean>>(PostResponse.Loading)
    private set




   suspend fun getPosts(): Response<List<Post>> {
        val posts = viewModelScope.async {
          PostRetrofitInstance.api.getPosts()
        }
       return posts.await()
    }


}
