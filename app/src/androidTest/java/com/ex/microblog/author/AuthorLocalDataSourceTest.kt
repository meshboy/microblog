package com.ex.microblog.author

import com.ex.microblog.core.data.author.entities.AuthorDao
import com.ex.microblog.core.data.author.entities.DatabaseAuthor
import com.ex.microblog.core.data.author.repository.AuthorLocalDataSource
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */


/**
 * Test the implementation of {@link AuthorLocalDataSource}
 */
@RunWith(MockitoJUnitRunner::class)
class AuthorLocalDataSourceTest {

    companion object {
        val AUTHORS = arrayListOf(
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

    lateinit var authorLocalDataSource: AuthorLocalDataSource

    @Mock
    lateinit var authorDao: AuthorDao

    @Throws(Exception::class)
    @Before
    fun setup() {
        authorLocalDataSource = AuthorLocalDataSource(authorDao)
    }

    @Test
    fun getAuthorsWithValidOffsetAndLimit() {

        val offset = 0
        val size = 2

//        return a list of mocked authors when dao is called
        `when`(authorDao.getAuthors(offset, size)).thenReturn(AUTHORS)

        val authors = authorLocalDataSource.fetchLocalAuthors(offset, size)
        assertTrue(authors.isNotEmpty())
        assertTrue(authors[0].name.isNotEmpty())
    }


    @Test
    fun getEmptyAuthorsWithInValidOffsetAndLimit() {

        val offset = 0
        val size = 2

//        return a list of authors when the offset is 0
        `when`(authorDao.getAuthors(offset, size)).thenReturn(AUTHORS)

//        check if the data returned is empty if the offset is beyond the valid data
        val authors = authorLocalDataSource.fetchLocalAuthors(3, size)
        assertTrue(authors.isEmpty())
    }
}