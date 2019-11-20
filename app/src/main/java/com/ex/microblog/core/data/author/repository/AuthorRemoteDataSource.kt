package com.ex.microblog.core.data.author.repository

import com.ex.microblog.core.data.author.dto.AuthorDto
import com.ex.microblog.core.exception.AuthorException
import com.ex.microblog.core.network.NetworkService
import com.ex.microblog.core.network.ResponseHandler

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */
class AuthorRemoteDataSource(private val networkService: NetworkService) {

    fun authorQuery(offset: Int, size: Int) = mapOf(
        "_page" to offset,
        "_limit" to size
    )

    /**
     * a default query values (offset and limit) to fetch authors. Successful authors from the handler
     * are returned or an error is thrown, if any of this is not the case, an empty list is returned
     */
    @Throws(AuthorException::class)
    suspend fun fetchAuthors(offset: Int = 0, size: Int = 20): List<AuthorDto> {

        return when(val authors = remoteAuthors(authorQuery(offset, size))) {
            is ResponseHandler.Success<List<AuthorDto>> -> {
                authors.response
            }
            is ResponseHandler.Error -> {
                throw AuthorException(authors.ex?.localizedMessage)
            }
            else -> emptyList()
        }
    }

    /**
     * fetch authors using query params and handle response if successful or fails
     */
    suspend fun remoteAuthors(query: Map<String, Int>): ResponseHandler<List<AuthorDto>> {
        return try {
            val result = networkService.fetchAuthors(query).await()
            ResponseHandler.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseHandler.Error(ex)
        }
    }
}
