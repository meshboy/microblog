package com.ex.microblog.post

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ex.microblog.core.data.database.MicroBlogDatabase
import com.ex.microblog.core.data.post.entities.DatabasePost
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */

/**
 * Test the implementation of {@link PostDao}
 */
@RunWith(AndroidJUnit4::class)
class PostDaoTest {


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

    private lateinit var microBlogDatabase: MicroBlogDatabase

    @Throws(Exception::class)
    @Before
    fun initDb() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        microBlogDatabase = MicroBlogDatabase.getDatabase(appContext)
    }

    @After
    fun tearDown() {
        microBlogDatabase.postDao.deleteAll()
        microBlogDatabase.close()
    }

    @Test
    fun insertPostAndGetPosts() {

//        when inserting new posts in the data source
        microBlogDatabase.postDao.insert(POSTS)

//        the posts can be retrieved afterwards
        val dbPost = microBlogDatabase.postDao.getPosts(1, 0, 10)
        Assert.assertTrue(dbPost.isNotEmpty())
        Assert.assertEquals(POSTS.title, dbPost[0].title)
        Assert.assertEquals(POSTS.authorId, dbPost[0].authorId)
    }
}