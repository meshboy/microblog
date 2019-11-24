package com.ex.microblog.authordetails.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ex.microblog.R
import com.ex.microblog.core.convertDateToGivenFormat
import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.getDateFromDateConstructor
import com.ex.microblog.core.loadImage

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * binds post title to textview
 */
@BindingAdapter("postTitle")
fun TextView.setPostTile(post: Post) {
    text = post.title
}


/**
 * binds post date to textview
 */
@BindingAdapter("postDate")
fun TextView.setPostDate(post: Post) {
    val dateString = post.date
    val getDate = getDateFromDateConstructor(dateString)
    text = convertDateToGivenFormat(date = getDate, format = "dd MMMM, yyyy")
}

/**
 * binds post time to textview
 */
@BindingAdapter("postTime")
fun TextView.setPostTime(post: Post) {
    val dateString = post.date
    val getDate = getDateFromDateConstructor(dateString)
    text = convertDateToGivenFormat(date = getDate, format = "HH:mm:ss a")
}


/**
 * binds post content into content view. Text longer than 150 should be ended with ellipsis, otherwise
 * display the whole body
 */
@BindingAdapter("postContent")
fun TextView.setPostContent(post: Post) {
    text = if (post.body.length > 200) post.body.substring(0, 200).plus("...") else post.body
}

/**
 * binds post image to imageview
 */
@BindingAdapter("postImage")
fun ImageView.setPostImage(post: Post) {
    loadImage(post.imageUrl, drawable = R.drawable.ic_default_post_image)
}