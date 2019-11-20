package com.ex.microblog.core.data.author.domain

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * holds author data displayed on the view
 */
data class Author(
    val id: String,
    val name: String,
    val userName: String,
    val email: String,
    val avatarUrl: String,
    val latitude: String,
    val longitude: String
)