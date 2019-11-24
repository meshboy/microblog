package com.ex.microblog.authordetails.view

import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.mvvm.BaseView

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * design to hold author's details view
 */
interface AuthorDetailsView: BaseView {
    /*
    hide the view that display error
     */
    fun hideError()

    fun navigateToPostDetailsPage(post: Post)
}