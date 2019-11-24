package com.ex.microblog.core.data.post.domain

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * Defines post to hold items on the view
 */
data class Post(
    val id: Int,
    val date: String,
    val title: String,
    val body: String,
    val imageUrl: String,
    val authorId: Int
)