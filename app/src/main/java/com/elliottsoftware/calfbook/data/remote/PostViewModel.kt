package com.elliottsoftware.calfbook.data.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class PostViewModel:ViewModel() {



   suspend fun getPosts(): Response<List<Post>> {
        val posts = viewModelScope.async {
          PostRetrofitInstance.api.getPosts()
        }
       return posts.await()
    }


}