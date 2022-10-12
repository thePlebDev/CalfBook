package com.elliottsoftware.calfbook.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>
}