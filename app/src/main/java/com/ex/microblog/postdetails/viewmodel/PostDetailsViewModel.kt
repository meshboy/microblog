package com.ex.microblog.postdetails.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ex.microblog.core.data.comments.domain.Comment
import com.ex.microblog.core.data.comments.repository.CommentRepository
import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.exception.CommentException
import com.ex.microblog.core.mvvm.BaseViewModel
import com.ex.microblog.postdetails.view.PostDetailsView
import kotlinx.coroutines.*

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */
class PostDetailsViewModel(private val commentRepository: CommentRepository) :
    BaseViewModel<PostDetailsView>() {

    private val viewJob = Job()

    private val coroutineJob = CoroutineScope(viewJob + Dispatchers.Main)

    val post = MutableLiveData<Post>()

    val comments = MutableLiveData<List<Comment>>()

    val offset = MutableLiveData<Int>()

    //    init loading state
    val isLoading = MutableLiveData<Boolean>()

    //    load more authors flag
    val isLoadingMore = MutableLiveData<Boolean>()

    init {
        offset.value = 0
        comments.value = emptyList()

        isLoading.value = false
        isLoadingMore.value = false
    }

    /**
     * assign the fetched authors to mutable author list in the main thread.
     * for whatever reason, if the offset is null, the default offset value used should
     * be set back to 1
     */
    fun fetchComments() {
        /*
          *isLoading is expected to be true when the comments is empty which is an attempt to
          * fetch new comments
          */
        isLoading.value = comments.value?.isEmpty()
        isLoadingMore.value = comments.value?.isNotEmpty()
        coroutineJob.launch {
            try {
                val fetchedComments =
                    fetchCommentsFromDataSource(post.value!!.authorId, offset.value ?: 1)
                if (fetchedComments.isNotEmpty()) {
                    comments.value = comments.value?.plus(fetchedComments)
                }
            } catch (ex: CommentException) {
                ex.printStackTrace()
                view.showError(ex.msg)
            } finally {
                isLoadingMore.value = false
                isLoading.value = false
            }
        }
    }

    @Throws(CommentException::class)
    suspend fun fetchCommentsFromDataSource(
        postId: Int,
        offset: Int,
        size: Int = 5
    ): List<Comment> {
        return withContext(Dispatchers.IO) {
            commentRepository.comments(postId, offset, size)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewJob.cancel()
    }
}