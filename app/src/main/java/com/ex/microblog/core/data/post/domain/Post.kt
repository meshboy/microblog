package com.ex.microblog.core.data.post.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * Defines post to hold items on the view
 *
 * Post is Parcelable to ensure it is passed from one view (e.g fragment) to another
 */
@Parcelize
data class Post(
    val id: Int,
    val date: String,
    val title: String,
    val body: String,
    val imageUrl: String,
    val authorId: Int
): Parcelable