package com.elliottsoftware.calfbook.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostRetrofitInstance {

    val api:PostApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }
}