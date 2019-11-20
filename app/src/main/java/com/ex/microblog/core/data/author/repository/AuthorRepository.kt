package com.ex.microblog.core.data.author.repository

import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.author.entities.DatabaseAuthor

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

interface AuthorRepository {
    suspend fun authors(offset: Int, size: Int): List<Author>
    suspend fun saveAuthor(vararg databaseAuthor: DatabaseAuthor)
}