package com.ex.microblog.core.data.author.dto

import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.author.entities.DatabaseAuthor
import com.squareup.moshi.JsonClass

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * holds authors spec from a remote source
 */
@JsonClass(generateAdapter = true)
data class AuthorDto(
    val id: Long?,
    val name: String?,
    val userName: String?,
    val email: String?,
    val avatarUrl: String?,
    val address: AuthorAddress?
)

data class AuthorAddress(
    val latitude: String?,
    val longitude: String?
)

/**
 * transform author dto schema to databaseAuthor schema
 */
fun List<AuthorDto>.asDatabaseModel(): List<DatabaseAuthor> {
    return map {
        DatabaseAuthor(
            id = it.id ?: 0,
            name = it.name ?: "",
            userName = it.userName ?: "",
            email = it.email ?: "",
            avatarUrl = it.avatarUrl ?: "",
            addressLat = it.address?.latitude ?: "",
            addressLong = it.address?.longitude ?: ""
        )
    }
}


/**
 * transform author dto schema to domain author
 * this is used when the data is fetched from the server to serve the view immediately
 */
fun List<AuthorDto>.asDomainModel(): List<Author> {
    return map {
        Author(
            id = "${it.id ?: 0}",
            name = it.name ?: "",
            userName = it.userName ?: "",
            email = it.email ?: "",
            avatarUrl = it.avatarUrl ?: "",
            latitude = it.address?.latitude ?: "",
            longitude = it.address?.longitude ?: ""
        )
    }
}