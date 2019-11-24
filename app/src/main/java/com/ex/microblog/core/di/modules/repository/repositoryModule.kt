package com.ex.microblog.core.di.modules.repository

import com.ex.microblog.core.data.author.repository.AuthorRepository
import com.ex.microblog.core.data.author.repository.AuthorRepositoryImpl
import com.ex.microblog.core.data.comments.repository.CommentRepository
import com.ex.microblog.core.data.comments.repository.CommentRepositoryImpl
import com.ex.microblog.core.data.post.repository.PostRepository
import com.ex.microblog.core.data.post.repository.PostRepositoryImpl
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


    /**
     * LocalDataSource and RemoteDataSource are provided via Kodein instances which are mapped to
     * the PostRepository
     *
     * PostRepository is ready to be injected
     */
    bind<PostRepository>() with singleton { PostRepositoryImpl(instance(), instance()) }

    /**
     * RemoteDataSource are provided via Kodein instances which are mapped to
     * the CommentRepository
     *
     * CommentRepository is ready to be injected
     */
    bind<CommentRepository>() with singleton { CommentRepositoryImpl(instance()) }
}