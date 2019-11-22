package com.ex.microblog.author

import android.os.Build
import com.ex.microblog.authors.viewmodel.AuthorsListViewModel
import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.author.repository.AuthorRepository
import com.ex.microblog.core.exception.AuthorException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-22
 */

/**
 * Test the implementation of {@link AuthorsListViewModel}
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AuthorListViewModelTest {

    private lateinit var viewModel: AuthorsListViewModel

    @Mock
    lateinit var authorRepository: AuthorRepository

    private val expectedAuthors = listOf(
        Author(
            id = "1",
            name = "test name",
            userName = "test username",
            email = "test@gmail.com",
            avatarUrl = "test avatar",
            latitude = "3.5",
            longitude = "5.6"
        ),

        Author(
            id = "2",
            name = "test name 2",
            userName = "test username 2",
            email = "test2@gmail.com",
            avatarUrl = "test avatar",
            latitude = "3.5",
            longitude = "5.6"
        )
    )

    private val offset = 1
    private val limit = 20


    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Before
    fun setup() {

        viewModel = AuthorsListViewModel(authorRepository)
    }

    @Test
    fun loadSuccessfulAuthorsFromRepository() = runBlocking {

        `when`(authorRepository.authors(offset, limit)).thenReturn(expectedAuthors)

        val actualAuthors = viewModel.fetchAuthorsFromDataSource(offset)

        assertEquals(actualAuthors, expectedAuthors)

        return@runBlocking
    }

    @Test(expected = AuthorException::class)
    fun shouldThrowAnErrorWhenRepositoryHasException() = runBlocking {
        assertNotNull(viewModel.offset.value)

        `when`(
            authorRepository.authors(
                offset,
                limit
            )
        ).thenAnswer { throw AuthorException("No Internet") }

        viewModel.fetchAuthorsFromDataSource(offset)

        return@runBlocking
    }
}