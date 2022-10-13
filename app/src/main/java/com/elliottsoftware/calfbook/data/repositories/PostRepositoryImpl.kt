package com.elliottsoftware.calfbook.data.repositories

import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.data.remote.PostApi
import com.elliottsoftware.calfbook.data.remote.PostRetrofitInstance
import retrofit2.Response

class PostRepositoryImp(
    val api:PostApi = PostRetrofitInstance.api
) {
    //TODO:update this to make is return a response object for handling errors and loading
    suspend fun getPosts():List<Post>{
        return api.getPosts().body()!!
    }
}