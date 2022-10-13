package com.elliottsoftware.calfbook.data.repositories

import android.util.Log
import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.data.remote.PostApi
import com.elliottsoftware.calfbook.data.remote.PostRetrofitInstance
import com.elliottsoftware.calfbook.domain.models.PostResponse
import com.elliottsoftware.calfbook.domain.models.Response2
import com.elliottsoftware.calfbook.domain.repository.PostRepository
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class PostRepositoryImp(
    val api:PostApi = PostRetrofitInstance.api
):PostRepository {
    //TODO:update this to make is return a response object for handling errors and loading
//    override suspend fun getPosts():List<Post>{
//        return api.getPosts().body()!!
//    }

    override suspend fun getPosts() = flow {
        try {
            val data = api.getPosts().body()!!
            emit(PostResponse.Success(data))
        }catch (e:Exception){
            Log.e("PostRepositoryImpException",e.message.toString())
            emit(PostResponse.Failure("Error!"))
        }
    }
}