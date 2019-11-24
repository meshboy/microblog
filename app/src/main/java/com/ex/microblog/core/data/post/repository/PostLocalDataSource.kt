package com.ex.microblog.core.data.post.repository

import com.ex.microblog.core.data.post.entities.DatabasePost
import com.ex.microblog.core.data.post.entities.PostDao

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */
class PostLocalDataSource(private val postDao: PostDao) {

    fun fetchLocalPosts(authorId: Int, offset: Int, limit: Int): List<DatabasePost> {
        return postDao.getPosts(authorId, offset, limit)
    }

    fun savePosts(vararg databasePost: DatabasePost) {
        postDao.insert(*databasePost)
    }

    /**
     * delete all posts
     */
    fun deleteLocalPosts() {
        postDao.deleteAll()
    }
}