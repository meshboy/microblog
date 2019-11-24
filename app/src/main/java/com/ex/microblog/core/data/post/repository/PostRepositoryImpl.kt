package com.ex.microblog.core.data.post.repository

import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.data.post.dto.asDatabaseModel
import com.ex.microblog.core.data.post.dto.asDomainModel
import com.ex.microblog.core.data.post.entities.DatabasePost
import com.ex.microblog.core.data.post.entities.asDomainModel
import com.ex.microblog.core.exception.PostException

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */
class PostRepositoryImpl(
    private val postLocalDataSource: PostLocalDataSource,
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {

    @Throws(PostException::class)
    override suspend fun posts(authorId: Int, offset: Int, size: Int): List<Post> {

        /*
        the offset used locally is not in pages (1, 2, 3) but in sizes (0, 20, 40)
         */

        val localOffset = (offset - 1) * size

        val localPosts = postLocalDataSource.fetchLocalPosts(authorId, localOffset, size)

        if (localPosts.isNotEmpty())
            return localPosts.asDomainModel()


        val remotePosts = postRemoteDataSource.fetchPosts(authorId, offset, size)
        if (remotePosts.isNotEmpty()) {
            /*
             update the local source with the newest data
             */
            postLocalDataSource.savePosts(*remotePosts.asDatabaseModel().toTypedArray())
            return remotePosts.asDomainModel()
        }
        return emptyList()
    }

    override suspend fun savePost(vararg databasePost: DatabasePost) {
        postLocalDataSource.savePosts(*databasePost)
    }

    @Throws(PostException::class)
    override suspend fun syncLocalAndRemoteData(authorId: Int, offset: Int, size: Int): List<Post> {

        val remotePosts = postRemoteDataSource.fetchPosts(authorId, offset, size)

        /*
         clear up the local cached posts when the remote posts is not empty. This will update
         the local posts with the most updated data
         */
        if (remotePosts.isNotEmpty()) {
            postLocalDataSource.deleteLocalPosts()
            postLocalDataSource.savePosts(*remotePosts.asDatabaseModel().toTypedArray())
        }

        return remotePosts.asDomainModel()
    }
}