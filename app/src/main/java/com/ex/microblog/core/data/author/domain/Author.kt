package com.ex.microblog.core.data.author.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * holds author data displayed on the view
 *
 * Author is Parcelize to ensure it is passed from one view (e.g fragment) to another
 */
@Parcelize
data class Author(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val avatarUrl: String,
    val latitude: String,
    val longitude: String
) : Parcelable