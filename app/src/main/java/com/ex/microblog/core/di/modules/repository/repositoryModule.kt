package com.ex.microblog.core.di.modules.repository

import com.ex.microblog.core.data.author.repository.AuthorRepository
import com.ex.microblog.core.data.author.repository.AuthorRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * holds repository bindings used in viewmodels
 */
val repositoryModule = Kodein.Module("Repository Module") {

    /**
     * LocalDataSource and RemoteDataSource are provided via Kodein instances which are mapped to
     * the AuthorRepository
     *
     * AuthorRepository is ready to be injected
     */
    bind<AuthorRepository>() with singleton { AuthorRepositoryImpl(instance(), instance()) }
}