package com.ex.microblog.core.data.author.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * dao for author's related activities
 */
@Dao
interface AuthorDao {

    @Query("SELECT * FROM authors LIMIT :size OFFSET :offset")
    fun getAuthors(offset: Int, size: Int): List<DatabaseAuthor>

    /**
     * replace old object when there is a difference between the old object and the new object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg databaseAuthor: DatabaseAuthor)

    @Query("DELETE FROM authors")
    fun deleteAll()
}