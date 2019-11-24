package com.ex.microblog.core.data.comments.repository

import com.ex.microblog.core.data.comments.domain.Comment

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */

/**
 * Defines Comment repository single source of truth spec
 */
interface CommentRepository {
    suspend fun comments(postId: Int, offset: Int, size: Int): List<Comment>
}