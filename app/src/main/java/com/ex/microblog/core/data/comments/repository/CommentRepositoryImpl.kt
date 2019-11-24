package com.ex.microblog.core.data.comments.repository

import com.ex.microblog.core.data.comments.domain.Comment
import com.ex.microblog.core.data.comments.dto.asDomainModel
import com.ex.microblog.core.exception.CommentException

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */

/**
 * Defines Comment repository Implementation
 */
class CommentRepositoryImpl(
    private val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    @Throws(CommentException::class)
    override suspend fun comments(postId: Int, offset: Int, size: Int): List<Comment> {

        val commentDtos = commentRemoteDataSource.fetchComments(postId, offset, size)
        return commentDtos.asDomainModel()
    }
}