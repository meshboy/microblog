package com.ex.microblog.authors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ex.microblog.core.data.author.repository.AuthorRepository

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * factory class to provide the desired viewmodel class (AuthorListViewModel)
 */
class AuthorListViewModelFactory(private val authorRepository: AuthorRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorsListViewModel::class.java)) {
            return AuthorsListViewModel(authorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}