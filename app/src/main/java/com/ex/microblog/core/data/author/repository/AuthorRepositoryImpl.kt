package com.ex.microblog.core.data.author.repository

import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.author.dto.asDatabaseModel
import com.ex.microblog.core.data.author.dto.asDomainModel
import com.ex.microblog.core.data.author.entities.DatabaseAuthor
import com.ex.microblog.core.data.author.entities.asDomainModel
import timber.log.Timber

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

        /*
         the offset used locally is not in pages (1, 2, 3) but in sizes (0, 20, 40 )
         */
        val localOffset = (offset - 1) * size

        val localAuthors = authorLocalDataSource.fetchLocalAuthors(localOffset, size)

        /*
        if the data rage (offset and limit) is found locally, return local data to the client
         */
        if (localAuthors.isNotEmpty())
            return localAuthors.asDomainModel()


        val remoteAuthors = authorRemoteDataSource.fetchAuthors(offset, size)
        if (remoteAuthors.isNotEmpty()) {
            /*
             update the local source with the newest data
             */
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