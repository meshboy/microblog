package com.ex.microblog.authors.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.loadImage
import de.hdodenhof.circleimageview.CircleImageView

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-21
 */

/**
 * bind author's avatar into image view
 */
@BindingAdapter("authorImage")
fun CircleImageView.setAuthorImage(author: Author) {
    loadImage(author.avatarUrl)
}

/**
 * bind author's name into text view
 */
@BindingAdapter("authorName")
fun TextView.setAuthorName(author: Author) {
    text = author.name
}

/**
 * bind author's email into text view
 */
@BindingAdapter("authorEmail")
fun TextView.setAuthorEmail(author: Author) {
    text = author.email
}