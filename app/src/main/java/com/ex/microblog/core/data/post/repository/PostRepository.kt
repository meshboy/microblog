package com.ex.microblog.core.data.post.repository

import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.data.post.entities.DatabasePost

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */
interface PostRepository {
    suspend fun posts(authorId: Int, offset: Int, size: Int): List<Post>
    suspend fun savePost(vararg databasePost: DatabasePost)
    suspend fun syncLocalAndRemoteData(authorId: Int, offset: Int, size: Int): List<Post>
}