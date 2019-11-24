package com.ex.microblog.postdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ex.microblog.core.data.comments.repository.CommentRepository

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */
/**
 * factory class to provide the desired viewmodel class (PostDetailsViewModel)
 */
class PostDetailsViewModelFactory(
    private val commentRepository: CommentRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostDetailsViewModel::class.java)) {
            return PostDetailsViewModel(commentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}