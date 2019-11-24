package com.ex.microblog.authors.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ex.microblog.authors.view.AuthorListView
import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.author.repository.AuthorRepository
import com.ex.microblog.core.exception.AuthorException
import com.ex.microblog.core.mvvm.BaseViewModel
import kotlinx.coroutines.*

class AuthorsListViewModel(private val authorRepository: AuthorRepository) :
    BaseViewModel<AuthorListView>() {

    val LIMIT = 20

    private val viewJob = Job()

    private val coroutineJob = CoroutineScope(viewJob + Dispatchers.Main)

    val authors = MutableLiveData<List<Author>>()

    val offset = MutableLiveData<Int>()

    //    init loading state
    val isLoading = MutableLiveData<Boolean>()

    //    load more authors flag
    val isLoadingMore = MutableLiveData<Boolean>()

    init {
        offset.value = 0
        authors.value = emptyList()

        isLoading.value = false
        isLoadingMore.value = false
    }

    /**
     * assign the fetched authors to mutable author list in the main thread.
     * for whatever reason, if the offset is null, the default offset value used should
     * be set back to 1
     */
    fun fetchAuthors() {
        /*
          *isLoading is expected to be true when the autors is empty which is an attempt to
          * fetch new authors
          */
        isLoading.value = authors.value?.isEmpty()
        isLoadingMore.value = authors.value?.isNotEmpty()
        coroutineJob.launch {
            try {
                val fetchedAuthors = fetchAuthorsFromDataSource(offset.value ?: 1)
                if (fetchedAuthors.isNotEmpty()) {
                    authors.value = authors.value?.plus(fetchedAuthors)
                }
            } catch (ex: AuthorException) {
                ex.printStackTrace()
                view.showError(ex.msg)
            } finally {
                isLoadingMore.value = false
                isLoading.value = false
            }
        }
    }

    @Throws(AuthorException::class)
    suspend fun fetchAuthorsFromDataSource(offset: Int): List<Author> {
        return withContext(Dispatchers.IO) {
            authorRepository.authors(offset, LIMIT)
        }
    }

    /**
     * upon selection from the list of authors, the selected author
     * should be passed to the details page
     */
    fun navigateToAuthorDetailsPage(author: Author) {
        view.navigateToAuthorDetailsPage(author)
    }

    override fun onCleared() {
        super.onCleared()
        viewJob.cancel()
    }
}
