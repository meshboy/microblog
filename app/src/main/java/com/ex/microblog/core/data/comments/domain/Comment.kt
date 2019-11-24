package com.ex.microblog.core.data.comments.domain

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */
data class Comment(
    val userName: String,
    val email: String,
    val avatarUrl: String,
    val body: String,
    val date: String
)