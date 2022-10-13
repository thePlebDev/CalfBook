package com.elliottsoftware.calfbook.domain.use_cases

import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.data.repositories.PostRepositoryImp
import com.elliottsoftware.calfbook.domain.models.PostResponse
import com.elliottsoftware.calfbook.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val postRepository: PostRepository = PostRepositoryImp()
) {
    suspend operator fun invoke():Flow<PostResponse<List<Post>>> = postRepository.getPosts()

}