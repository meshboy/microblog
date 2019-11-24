package com.ex.microblog.post

import com.ex.microblog.core.data.post.entities.DatabasePost
import com.ex.microblog.core.data.post.entities.PostDao
import com.ex.microblog.core.data.post.repository.PostLocalDataSource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */

/**
 * Test the implementation of {@link PostLocalDataSource}
 */
@RunWith(MockitoJUnitRunner::class)
class PostLocalDataSourceTest {

    companion object {
         val POSTS = DatabasePost(
            id = 1,
            title = "title test",
            body = "body test",
            imageUrl = "image url test",
            authorId = 1,
            date = Date().toString()
        )
    }

    lateinit var postLocalDataSource: PostLocalDataSource

    @Mock
    lateinit var postDao: PostDao

    @Throws(Exception::class)
    @Before
    fun setup() {
        postLocalDataSource = PostLocalDataSource(postDao)
    }

    @Test
    fun getPostWithValidOffsetAndLimit() {

        val offset = 0
        val size = 2
        val authorId = 1

//        return a list of mocked posts when dao is called
        Mockito.`when`(postDao.getPosts(authorId, offset, size)).thenReturn(arrayListOf(POSTS))

        val posts = postLocalDataSource.fetchLocalPosts(authorId, offset, size)
        Assert.assertTrue(posts.isNotEmpty())
        Assert.assertTrue(posts[0].title.isNotEmpty())
    }


    @Test
    fun getEmptyAuthorsWithInValidOffsetAndLimit() {

        val offset = 0
        val size = 2
        val authorId = 1

//        return a list of posts when the offset is 0
        Mockito.`when`(postDao.getPosts(authorId, offset, size)).thenReturn(arrayListOf(POSTS))

//        check if the data returned is empty if the offset is beyond the valid data
        val posts = postLocalDataSource.fetchLocalPosts(3, offset, size)
        Assert.assertTrue(posts.isEmpty())
    }
}