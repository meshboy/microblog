package com.ex.microblog.author

import com.ex.microblog.core.data.author.dto.AuthorAddress
import com.ex.microblog.core.data.author.dto.AuthorDto
import com.ex.microblog.core.data.author.repository.AuthorRemoteDataSource
import com.ex.microblog.core.network.NetworkService
import com.ex.microblog.core.network.ResponseHandler
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException


/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * Test the implementation of {@link AuthorRemoteDataSource}
 */
@RunWith(MockitoJUnitRunner::class)
class AuthorRemoteDataSourceTest {

    @Mock
    lateinit var networkService: NetworkService

    lateinit var remoteDataSource: AuthorRemoteDataSource

    companion object {
        val AUTHORS = listOf(
            AuthorDto(
                id = 1,
                name = "Leigh Kessler",
                userName = "Myrtie_Heller51",
                avatarUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/nutzumi/128.jpg",
                email = "vance_hansen7@yahoo.com",
                address = AuthorAddress(
                    latitude = "73.5451",
                    longitude = "155.4534"
                )
            )
        )
    }

    @Before
    fun setup() {
        remoteDataSource = AuthorRemoteDataSource(networkService)
    }

    @Test
    fun serverCallWithSucess() {
//        set the query params
        val query = remoteDataSource.authorQuery(0, 1)

//        return success upon author list request
        `when`(networkService.fetchAuthorsAsync(query)).thenReturn(AUTHORS.toDeferred())

        runBlocking {

            val remoteAuthors = remoteDataSource.remoteAuthors(query)

//            check if the response returned is of type success
            assertTrue(remoteAuthors is ResponseHandler.Success<*>)
        }
    }


    @Test
    fun serverCallWithError() {
//        set the query params
        val query = remoteDataSource.authorQuery(0, 1)

//        return error upon author list request
        `when`(networkService.fetchAuthorsAsync(query)).thenThrow(HttpException::class.java)

        runBlocking {

            val remoteAuthors = remoteDataSource.remoteAuthors(query)

//            check if the response returned is of type error
            assertTrue(remoteAuthors is ResponseHandler.Error)
        }
    }

//    convert data to deferred
    fun <T> T.toDeferred() = CompletableDeferred(this)
}