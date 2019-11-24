package com.ex.microblog.authors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ex.microblog.R
import com.ex.microblog.authors.adapter.AuthorAdapter
import com.ex.microblog.authors.view.AuthorListView
import com.ex.microblog.authors.viewmodel.AuthorListViewModelFactory
import com.ex.microblog.authors.viewmodel.AuthorsListViewModel
import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.core.hide
import com.ex.microblog.core.mvvm.BaseFragment
import com.ex.microblog.core.show
import com.ex.microblog.databinding.AuthorsListFragmentBinding
import org.kodein.di.generic.instance
import timber.log.Timber

/**
 * defines list of authors
 */
class AuthorsListFragment : BaseFragment<AuthorListView>(), AuthorListView {

    /**
     * initialise view used in this author list life cycle
     */
    override fun createView(): AuthorListView = this

    companion object {
        fun newInstance() = AuthorsListFragment()
    }

    /**
     * provide via injection via kodein instance
     */
    private val viewModelFactory: AuthorListViewModelFactory by instance()

    /**
     * AuthorListViewModel is called at most once from the factory using view providers
     * The viewmodel uses the context of the AuthorsListFragment
     */
    val viewModel: AuthorsListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthorsListViewModel::class.java)
    }

    private lateinit var binding: AuthorsListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AuthorsListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        /*
         when the user resumes back to the view,
         ensure there is an increment in the offset to avoid duplicate items
         */
        viewModel.offset.value = viewModel.offset.value?.inc()

        Timber.d("offset ${viewModel.offset.value}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.view = createView()

        /*
            bind the fragment's view properties
         */
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.fetchAuthors()

        val adapter = AuthorAdapter()
        binding.authorsRecyclerView.adapter = adapter

//        load more when the user scrolls to the bottom of the list
        binding.authorsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//                ensure the user is not actively scrolling and at the bottom of the list before load more is called
                if (!binding.authorsRecyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    if (!viewModel.authors.value.isNullOrEmpty()) {
                        viewModel.offset.value = viewModel.offset.value?.inc()
                        viewModel.fetchAuthors()
                    }
                }
            }
        })

        /*
        select an item from the recyclerview list
         */
        adapter.setListener(AuthorAdapter.OnClickListener { author ->
            viewModel.navigateToAuthorDetailsPage(author)
        })

        viewModel.authors.observe(this, Observer { authors ->
            adapter.submitList(authors)
        })

        binding.tryAgainButton.setOnClickListener {
            binding.tryAgainButton.hide()
            binding.errorTextView.hide()
            viewModel.fetchAuthors()
        }

        /*
         * switch loader to appear or hide on the view depending
         * on the status of the flag. Loader is used when fetching list of
         * authors from the data source
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
    }

    override fun navigateToAuthorDetailsPage(author: Author) {
        if (findNavController().currentDestination?.id == R.id.authorsListFragment) {
            findNavController().navigate(
                AuthorsListFragmentDirections.actionAuthorsListFragmentToAuthorDetailsFragment(
                    author
                )
            )
        }
    }

    /**
     * the error layout should only displace the recylerview position
     * when the author's list is entirely empty. The user should only be
     * notified via a toast about what is going on when the author's list is
     * already populated
     */
    override fun showError(error: String?) {
        if (viewModel.authors.value.isNullOrEmpty()) {
            binding.tryAgainButton.show()
            binding.errorTextView.show()
            binding.errorTextView.text = error
        } else {
            Toast.makeText(activity!!, error, Toast.LENGTH_SHORT).show()
        }
    }

}
