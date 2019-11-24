package com.ex.microblog.core.data.comments.repository

import com.ex.microblog.core.data.comments.dto.CommentDto
import com.ex.microblog.core.exception.CommentException
import com.ex.microblog.core.network.NetworkService
import com.ex.microblog.core.network.ResponseHandler

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */
class CommentRemoteDataSource(private val networkService: NetworkService) {

    fun commentQuery(offset: Int, size: Int) = mapOf(
        "_page" to "$offset",
        "_limit" to "$size",
        "_sort" to "date",
        "_order" to "asc"
    )

    /**
     * a default query values (offset and limit) to fetch posts.
     */
    @Throws(CommentException::class)
    suspend fun fetchComments(postId: Int, offset: Int, size: Int): List<CommentDto> {

        return when (val comments = remoteComments(postId, commentQuery(offset, size))) {
            is ResponseHandler.Success<List<CommentDto>> -> {
                comments.response
            }
            is ResponseHandler.Error -> {
                throw CommentException(comments.ex?.localizedMessage)
            }
            else -> emptyList()
        }
    }

    /**
     * fetch comments using post id, query params and handle response if successful or fails
     */
    suspend fun remoteComments(
        postId: Int,
        query: Map<String, String>
    ): ResponseHandler<List<CommentDto>> {
        return try {
            val result = networkService.fetchCommentsByPostIdAsync(postId, query).await()
            ResponseHandler.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseHandler.Error(ex)
        }
    }
}