package com.ex.microblog.core.data.post.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

@Dao
interface PostDao {

    /**
     * fetch posts by its author id
     */
    @Query("SELECT * FROM posts WHERE author_id = :authorId LIMIT :size OFFSET :offset")
    fun getPosts(authorId: Int, offset: Int, size: Int): List<DatabasePost>

    /**
     * replace old object when there is a difference between the old object and the new object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg databasePost: DatabasePost)

    @Query("DELETE FROM posts")
    fun deleteAll()
}