package com.ex.microblog.core.data.post.repository

import com.ex.microblog.core.data.post.dto.PostDto
import com.ex.microblog.core.exception.PostException
import com.ex.microblog.core.network.NetworkService
import com.ex.microblog.core.network.ResponseHandler

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */
class PostRemoteDataSource(private val networkService: NetworkService) {

    fun postQuery(authorId: Int, offset: Int, size: Int) = mapOf(
        "_page" to offset,
        "_limit" to size,
        "authorId" to authorId
    )

    /**
     * a default query values (offset and limit) to fetch posts.
     */
    @Throws(PostException::class)
    suspend fun fetchPosts(authorId: Int, offset: Int, size: Int): List<PostDto> {

        return when (val authors = remotePosts(postQuery(authorId, offset, size))) {
            is ResponseHandler.Success<List<PostDto>> -> {
                authors.response
            }
            is ResponseHandler.Error -> {
                throw PostException(authors.ex?.localizedMessage)
            }
            else -> emptyList()
        }
    }

    /**
     * fetch posts using query params and handle response if successful or fails
     */
    suspend fun remotePosts(query: Map<String, Int>): ResponseHandler<List<PostDto>> {
        return try {
            val result = networkService.fetchPostsByAuthorIdAsync(query).await()
            ResponseHandler.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseHandler.Error(ex)
        }
    }
}