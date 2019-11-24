package com.ex.microblog.core.data.post.dto

import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.data.post.entities.DatabasePost

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * Defines Post's spec from remote source
 */
data class PostDto(
    val id: Int,
    val date: String,
    val title: String,
    val body: String,
    val imageUrl: String,
    val authorId: Int
)

/**
 * transform post dto schema to databasePost schema
 */
fun List<PostDto>.asDatabaseModel(): List<DatabasePost> {
    return map {
        DatabasePost(
            id = it.id,
            title = it.title,
            body = it.body,
            imageUrl = it.imageUrl,
            authorId = it.authorId,
            date = it.date
        )
    }
}


/**
 * transform post dto schema to domain post
 * this is used when the data is fetched from the server to serve the view immediately
 */
fun List<PostDto>.asDomainModel(): List<Post> {
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