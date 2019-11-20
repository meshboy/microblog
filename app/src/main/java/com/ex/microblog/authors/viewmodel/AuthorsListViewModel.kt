package com.ex.microblog.authors.viewmodel

import com.ex.microblog.authors.view.AuthorListView
import com.ex.microblog.core.data.author.repository.AuthorRepository
import com.ex.microblog.core.mvvm.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AuthorsListViewModel(val authorRepository: AuthorRepository) :
    BaseViewModel<AuthorListView>() {

    private val viewJob = Job()

    private val coroutineJob = CoroutineScope(viewJob + Dispatchers.Main)

    init {

    }

    override fun onCleared() {
        super.onCleared()
        viewJob.cancel()
    }
}
