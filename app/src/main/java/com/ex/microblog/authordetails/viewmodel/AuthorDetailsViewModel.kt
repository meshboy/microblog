package com.ex.microblog.authordetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ex.microblog.authordetails.view.AuthorDetailsView
import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.data.post.repository.PostRepository
import com.ex.microblog.core.exception.PostException
import com.ex.microblog.core.mvvm.BaseViewModel
import kotlinx.coroutines.*

class AuthorDetailsViewModel(private val postRepository: PostRepository) :
    BaseViewModel<AuthorDetailsView>() {

    val LIMIT = 10

    private val viewJob = Job()

    private val coroutineJob = CoroutineScope(viewJob + Dispatchers.Main)


    var author = MutableLiveData<Author>()

    val latitude: LiveData<String> = Transformations.map(author) {
        "Latitude: ${it.latitude}"
    }

    val longitude: LiveData<String> = Transformations.map(author) {
        "Longitude: ${it.longitude}"
    }

    val posts = MutableLiveData<List<Post>>()

    val offset = MutableLiveData<Int>()

    //    init loading state
    val isLoading = MutableLiveData<Boolean>()

    //    load more authors flag
    val isLoadingMore = MutableLiveData<Boolean>()

    init {
        offset.value = 0
        posts.value = emptyList()

        isLoading.value = false
        isLoadingMore.value = false
    }

    /**
     * assign the fetched posts to mutable post list in the main thread.
     * for whatever reason, if the offset is null, the default offset value used should
     * be set back to 1
     */
    fun fetchPosts() {
        view.hideError()
        /*
         *isLoading is expected to be true when the posts is empty which is an attempt to
         * fetch new posts
         */
        isLoading.value = posts.value?.isEmpty()
        isLoadingMore.value = posts.value?.isNotEmpty()
        coroutineJob.launch {
            try {
                val fetchedPosts = fetchPosts(author.value?.id!!, offset.value ?: 1)
                if (fetchedPosts.isNotEmpty()) {
                    posts.value = posts.value?.plus(fetchedPosts)
                }
            } catch (ex: PostException) {
                ex.printStackTrace()
                view.showError(ex.msg)
            } finally {
                isLoadingMore.value = false
                isLoading.value = false
            }
        }
    }

    fun refreshPosts() {
        view.hideError()

        isLoading.value = true

        coroutineJob.launch {
            try {
                offset.value = 1
                val syncedPosts = syncPosts(author.value?.id!!, offset.value ?: 1, LIMIT)
                if (syncedPosts.isNotEmpty()) {
                    posts.value = syncedPosts
                }
            } catch (ex: PostException) {
                ex.printStackTrace()
                view.showError(ex.msg)
            } finally {
                isLoading.value = false
            }
        }
    }

    @Throws(PostException::class)
    suspend fun fetchPosts(authorId: Int, offset: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            postRepository.posts(authorId, offset, LIMIT)
        }
    }

    @Throws(PostException::class)
    suspend fun syncPosts(authorId: Int, offset: Int, limit: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            postRepository.syncLocalAndRemoteData(authorId, offset, limit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewJob.cancel()
    }
}
