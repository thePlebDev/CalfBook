package com.elliottsoftware.calfbook.domain.use_cases

import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.data.repositories.PostRepositoryImp

class GetPostsUseCase(
    private val postRepository: PostRepositoryImp = PostRepositoryImp()
) {
    suspend operator fun invoke():List<Post> = postRepository.getPosts()
}