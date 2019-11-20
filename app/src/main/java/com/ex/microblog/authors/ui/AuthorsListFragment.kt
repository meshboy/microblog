package com.ex.microblog.authors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.ex.microblog.authors.view.AuthorListView
import com.ex.microblog.authors.viewmodel.AuthorListViewModelFactory
import com.ex.microblog.authors.viewmodel.AuthorsListViewModel
import com.ex.microblog.core.mvvm.BaseFragment
import com.ex.microblog.databinding.AuthorsListFragmentBinding
import org.kodein.di.generic.instance

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
    private val viewModel: AuthorsListViewModel by lazy {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.view = createView()

        /*
            bind the fragment's view properties
         */
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun showError(error: String?) {

    }

}
