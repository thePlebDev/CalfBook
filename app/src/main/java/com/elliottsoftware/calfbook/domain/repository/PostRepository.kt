package com.elliottsoftware.calfbook.domain.repository

import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.domain.models.PostResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPosts(): Flow<PostResponse<List<Post>>>
}