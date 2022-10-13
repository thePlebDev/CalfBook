package com.elliottsoftware.calfbook.domain.repository

import com.elliottsoftware.calfbook.data.remote.Post

interface PostRepository {

    suspend fun getPosts():List<Post>
}