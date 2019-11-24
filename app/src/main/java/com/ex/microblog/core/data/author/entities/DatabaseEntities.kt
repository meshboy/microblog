package com.ex.microblog.core.data.author.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ex.microblog.core.data.author.domain.Author

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * persists authors fetched from a remote source
 */
@Entity(tableName = "authors")
data class DatabaseAuthor(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    val email: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "address_latitude")
    val addressLat: String,

    @ColumnInfo(name = "address_longitude")
    val addressLong: String

)

/**
 * transform database spec to domain author
 */
fun List<DatabaseAuthor>.asDomainModel(): List<Author> {
    return map {
        Author(
            id = it.id,
            name = it.name,
            userName = it.userName,
            email = it.email,
            avatarUrl = it.avatarUrl,
            latitude = it.addressLat,
            longitude = it.addressLong
        )
    }
}