package com.ex.microblog.authordetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ex.microblog.core.data.post.repository.PostRepository

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */
class AuthorDetailsViewModelFactory(private val postRepository: PostRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorDetailsViewModel::class.java)) {
            return AuthorDetailsViewModel(postRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}