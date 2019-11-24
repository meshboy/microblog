package com.ex.microblog.core.data.post.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ex.microblog.core.data.post.domain.Post

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * persists authors fetched from a remote source
 */
@Entity(tableName = "posts")
data class DatabasePost(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val title: String,

    val body: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "author_id")
    val authorId: Int,

    val date: String
)

/**
 * transform database spec to domain author
 */
fun List<DatabasePost>.asDomainModel(): List<Post> {
    return map {
        Post(
            id = it.id,
            title = it.title,
            body = it.body,
            imageUrl = it.imageUrl,
            authorId = it.authorId,
            date = it.date
        )
    }
}