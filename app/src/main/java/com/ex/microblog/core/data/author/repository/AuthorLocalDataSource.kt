package com.ex.microblog.core.data.author.repository

import com.ex.microblog.core.data.author.entities.AuthorDao
import com.ex.microblog.core.data.author.entities.DatabaseAuthor

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */
class AuthorLocalDataSource (private val authorDao: AuthorDao) {

    fun fetchLocalAuthors(offset: Int, size: Int): List<DatabaseAuthor> {
        return authorDao.getAuthors(offset, size)
    }

    fun saveAuthors(vararg databaseAuthor: DatabaseAuthor) {
        authorDao.insert(*databaseAuthor)
    }
}