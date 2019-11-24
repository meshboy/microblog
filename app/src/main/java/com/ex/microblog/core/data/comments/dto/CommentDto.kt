package com.ex.microblog.core.data.comments.dto

import com.ex.microblog.core.data.comments.domain.Comment

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */
data class CommentDto(
    val id: Int,
    val userName: String,
    val email: String,
    val avatarUrl: String,
    val body: String,
    val postId: Int,
    val date: String
)

fun List<CommentDto>.asDomainModel(): List<Comment> {
    return map {
        Comment(
            userName = it.userName,
            email = it.email,
            avatarUrl = it.avatarUrl,
            body = it.body,
            date = it.date
        )
    }
}