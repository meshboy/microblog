package com.ex.microblog.postdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.microblog.R
import com.ex.microblog.core.hide
import com.ex.microblog.core.loadImage
import com.ex.microblog.core.mvvm.BaseFragment
import com.ex.microblog.core.show
import com.ex.microblog.databinding.PostDetailsFragmentBinding
import com.ex.microblog.postdetails.adapter.CommentAdapter
import com.ex.microblog.postdetails.view.PostDetailsView
import com.ex.microblog.postdetails.viewmodel.PostDetailsViewModel
import com.ex.microblog.postdetails.viewmodel.PostDetailsViewModelFactory
import org.kodein.di.generic.instance

class PostDetailsFragment : BaseFragment<PostDetailsView>(), PostDetailsView {

    override fun createView(): PostDetailsView = this

    companion object {
        fun newInstance() = PostDetailsFragment()
    }

    lateinit var binding: PostDetailsFragmentBinding

    /**
     * provide via injection via kodein instance
     */
    private val viewModelFactory: PostDetailsViewModelFactory by instance()

    /**
     * PostDetailsViewModel is called at most once from the factory using view providers
     * The viewmodel uses the context of the PostDetailsFragment
     */
    val viewModel: PostDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PostDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
        bind the fragment's view properties
         */
        viewModel.view = createView()

        /*
           access the post shared from AuthorsDetailFragment
         */
        viewModel.post.value = PostDetailsFragmentArgs.fromBundle(arguments!!).post

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.postImageView.loadImage(
            viewModel.post.value?.imageUrl,
            R.drawable.ic_default_post_image
        )

        viewModel.fetchComments()

        val commentAdapter = CommentAdapter()
        binding.commentRecyclerView.adapter = commentAdapter

        val itemDivider =
            DividerItemDecoration(binding.commentRecyclerView.context, LinearLayoutManager.VERTICAL)
        binding.commentRecyclerView.addItemDecoration(itemDivider)

        viewModel.comments.observe(this, Observer { comments ->

            //            ensure the views related to error info are hidden
            hideErrorViews()

            commentAdapter.submitList(comments)
        })


        //  load more when the user scrolls to the bottom of the list
        binding.commentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//                ensure the user is not actively scrolling and at the bottom of the list before load more is called
                if (!binding.commentRecyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    if (!viewModel.comments.value.isNullOrEmpty()) {
                        viewModel.offset.value = viewModel.offset.value?.inc()
                        viewModel.fetchComments()
                    }
                }
            }
        })

        /*
        * switch swipeRefresh loader to appear or hide on the view depending
        * on the status of the flag. Loader is used when fetching list of
        * comments from the data source
        */
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading)
                binding.progressBar.show()
            else
                binding.progressBar.hide()
        })

        /*
         * switch loader to appear or hide on the view depending
         * on the status of the flag. Loader more only appears when the user scrolls
         * down to the bottom of the list
         */
        viewModel.isLoadingMore.observe(this, Observer { isLoadingMore ->
            if (isLoadingMore)
                binding.loadMoreProgressBar.show()
            else
                binding.loadMoreProgressBar.hide()
        })

        /*
        reload the comments for the post
         */
        binding.tryAgainButton.setOnClickListener {
            viewModel.offset.value = 1

//            hide errror related views
            hideErrorViews()

            viewModel.fetchComments()
        }
    }

    fun hideErrorViews() {
        binding.tryAgainButton.hide()
        binding.errorMsgTextView.hide()
    }

    override fun showError(error: String?) {

        if (viewModel.comments.value!!.isEmpty()) {
            binding.tryAgainButton.show()
            binding.errorMsgTextView.show()
            binding.errorMsgTextView.text = error
        }
    }
}
