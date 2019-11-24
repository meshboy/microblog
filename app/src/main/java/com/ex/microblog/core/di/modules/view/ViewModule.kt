package com.ex.microblog.core.di.modules.view

import com.ex.microblog.authordetails.viewmodel.AuthorDetailsViewModelFactory
import com.ex.microblog.authors.viewmodel.AuthorListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * viewModule holds ViewModel bindings. ViewModel are served to the activity or fragment
 * via factory methods
 */
val viewModule = Kodein.Module("View Module") {

    /**
     * AuthorListViewModelFactory is dependency ready, the injected repository is provided
     * via an instance by kodein.
     * AuthorListViewModelFactory bind to the same name, which is provided anytime its called
     * by AuthorFragment
     */
    bind() from provider { AuthorListViewModelFactory(instance()) }


    /**
     * AuthorDetailsViewModelFactory is dependency ready, the injected repository is provided
     * via an instance by kodein.
     * AuthorDetailsViewModelFactory bind to the same name, which is provided anytime its called
     * by AuthorDetailsFragment
     */
    bind() from provider { AuthorDetailsViewModelFactory(instance()) }
}