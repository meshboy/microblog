package com.ex.microblog.postdetails.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ex.microblog.R
import com.ex.microblog.core.convertDateToGivenFormat
import com.ex.microblog.core.data.comments.domain.Comment
import com.ex.microblog.core.getDateFromDateConstructor
import com.ex.microblog.core.loadImage

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */


/**
 * binds comment username to textview
 */
@BindingAdapter("commentUsername")
fun TextView.setCommentUsername(comment: Comment) {
    text = comment.userName
}


/**
 * binds comment date to textview
 */
@SuppressLint("SetTextI18n")
@BindingAdapter("commentDate")
fun TextView.setCommentDate(comment: Comment) {
    val dateString = comment.date
    val getDate = getDateFromDateConstructor(dateString)

    /*
     * access only date from date string
     */
    val date = convertDateToGivenFormat(date = getDate, format = "MMM dd, yy")

    /*
     * access only time from date string
     */
    val time = convertDateToGivenFormat(date = getDate, format = "HH:mm")

    text = "$date @ $time"
}

/**
 * binds comment content to textview
 */
@BindingAdapter("commentBody")
fun TextView.setCommentBody(comment: Comment) {
    text = comment.body
}

/**
 * binds comment image to imageview
 */
@BindingAdapter("commentImage")
fun ImageView.setCommentImage(comment: Comment) {
    loadImage(comment.avatarUrl, drawable = R.drawable.ic_default_image_profile)
}