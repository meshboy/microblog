package com.ex.microblog.author

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ex.microblog.core.data.author.entities.DatabaseAuthor
import com.ex.microblog.core.data.database.MicroBlogDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * Test the implementation of {@link AuthorDao}
 */
@RunWith(AndroidJUnit4::class)
class AuthorDaoTest {

    companion object {
        val AUTHORS = arrayOf(
            DatabaseAuthor(
                id = 1,
                name = "Test Author 1",
                userName = "test 1",
                email = "test1@gmail.com",
                avatarUrl = "testUrl",
                addressLong = "4.0",
                addressLat = "5.0"
            ),

            DatabaseAuthor(
                id = 2,
                name = "Test Author 2",
                userName = "test2",
                email = "test2@gmail.com",
                avatarUrl = "testUrl",
                addressLong = "4.0",
                addressLat = "5.0"
            )
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
        microBlogDatabase.authorDao.deleteAll()
        microBlogDatabase.close()
    }

    @Test
    fun insertAuthorAndGetAuthors() {

//        when inserting new authors in the data source
        microBlogDatabase.authorDao.insert(*AUTHORS)

//        the authors can be retrieved afterwards
        val dbAuthor = microBlogDatabase.authorDao.getAuthors(0, 2)
        assertTrue(dbAuthor.isNotEmpty())
        assertEquals(AUTHORS.size, dbAuthor.size)
        assertEquals(AUTHORS[0].name, dbAuthor[0].name)
    }
}