package com.ex.microblog.core.data.author.repository

import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.author.dto.asDatabaseModel
import com.ex.microblog.core.data.author.dto.asDomainModel
import com.ex.microblog.core.data.author.entities.DatabaseAuthor
import com.ex.microblog.core.data.author.entities.asDomainModel

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * This class serves as the source of truth between the local source and the remote source
 */
class AuthorRepositoryImpl(
    private val authorLocalDataSource: AuthorLocalDataSource,
    private val authorRemoteDataSource: AuthorRemoteDataSource
) : AuthorRepository {


    /**
     * check the local source if the authors can be found
     * if the authors can't be found in the local source, send the request to the remote source
     */
    override suspend fun authors(offset: Int, size: Int): List<Author> {

        val localAuthors = authorLocalDataSource.fetchLocalAuthors(offset, size)

        if (localAuthors.isNotEmpty())
            return localAuthors.asDomainModel()


        val remoteAuthors = authorRemoteDataSource.fetchAuthors(offset, size)
        if (remoteAuthors.isNotEmpty()) {
            authorLocalDataSource.saveAuthors(*remoteAuthors.asDatabaseModel().toTypedArray())
            return remoteAuthors.asDomainModel()
        }
        return emptyList()
    }

    /**
     *save n numbers of authors in the local source
     */
    override suspend fun saveAuthor(vararg databaseAuthor: DatabaseAuthor) {
        authorLocalDataSource.saveAuthors(*databaseAuthor)
    }
}