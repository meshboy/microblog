package com.ex.microblog.authordetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ex.microblog.R
import com.ex.microblog.authordetails.adapter.PostAdapter
import com.ex.microblog.authordetails.view.AuthorDetailsView
import com.ex.microblog.authordetails.viewmodel.AuthorDetailsViewModel
import com.ex.microblog.authordetails.viewmodel.AuthorDetailsViewModelFactory
import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.core.hide
import com.ex.microblog.core.loadImage
import com.ex.microblog.core.mvvm.BaseFragment
import com.ex.microblog.core.show
import com.ex.microblog.databinding.AuthorDetailsFragmentBinding
import org.kodein.di.generic.instance

/**
 * Defines details of a selected author
 */
class AuthorDetailsFragment : BaseFragment<AuthorDetailsView>(), AuthorDetailsView {

    override fun createView(): AuthorDetailsView = this

    companion object {
        fun newInstance() = AuthorDetailsFragment()
    }

    lateinit var binding: AuthorDetailsFragmentBinding

    /**
     * provide via injection via kodein instance
     */
    private val viewModelFactory: AuthorDetailsViewModelFactory by instance()

    /**
     * uthorDetailsViewModel is called at most once from the factory using view providers
     * The viewmodel uses the context of the AuthorDetailsFragment
     */
    val viewModel: AuthorDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthorDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AuthorDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        /*
        when the user resumes back to the view,
        ensure there is an increment in the offset to avoid duplicate items
        */
        viewModel.offset.value = viewModel.offset.value?.inc()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.view = createView()

        /*
        access the author object passed from list author fragment
         */
        viewModel.author.value = AuthorDetailsFragmentArgs.fromBundle(arguments!!).author

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        /*
         load the avatar image into the profile ImageView
         */
        viewModel.author.value?.avatarUrl?.let {
            binding.authorProfileImageView.loadImage(it)
        }

        viewModel.fetchPosts()

        /*
        Use the username of the author as the toolbar title. The toolbar is accessed from the
        parent view (MainActivity)
         */
        val toolbar = activity?.findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        toolbar.title = viewModel.author.value?.userName ?: getString(R.string.authors)

        val adapter = PostAdapter()
        binding.postRecyclerView.adapter = adapter

        //        load more when the user scrolls to the bottom of the list
        binding.postRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//                ensure the user is not actively scrolling and at the bottom of the list before load more is called
                if (!binding.postRecyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    if (!viewModel.posts.value.isNullOrEmpty()) {
                        viewModel.offset.value = viewModel.offset.value?.inc()
                        viewModel.fetchPosts()
                    }
                }
            }
        })

        /*
          select an item from the recyclerview list
         */
        adapter.setListener(PostAdapter.OnClickListener { post ->
            viewModel.navigateToPostDetailsPage(post)
        })

        viewModel.posts.observe(this, Observer { authors ->
            adapter.submitList(authors)
        })

        /*
         * switch swipeRefresh loader to appear or hide on the view depending
         * on the status of the flag. Loader is used when fetching list of
         * posts from the data source
         */
        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
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

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPosts()
        }
    }

    override fun hideError() {
        binding.errorMsgTextView.hide()
    }

    override fun navigateToPostDetailsPage(post: Post) {
        if (findNavController().currentDestination?.id == R.id.authorDetailsFragment) {
            findNavController().navigate(
                AuthorDetailsFragmentDirections.actionAuthorDetailsFragmentToPostDetailsFragment(
                    post
                )
            )
        }
    }

    override fun showError(error: String?) {
        binding.errorMsgTextView.show()
        binding.errorMsgTextView.text = error
    }
}
