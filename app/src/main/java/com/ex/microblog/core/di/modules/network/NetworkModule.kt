package com.ex.microblog.core.di.modules.network

import com.ex.microblog.core.data.author.repository.AuthorRemoteDataSource
import com.ex.microblog.core.network.NetworkService
import com.ex.microblog.core.network.NetworkServiceImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * Network Module holds all network bindings. All bindings interfaces are directly mapped to
 * their implementations
 */
val networkModule = Kodein.Module("Network Module") {

    /**
     * Getting an instance of the network service should only happen once using singleton from kodein,
     * any other request should be served the same instance firstly created.
     *
     * Network Service is ready to be injected
     */
    bind<NetworkService>() with singleton { NetworkServiceImpl(instance()).api() }

    /**
     * Get an instance of remote data source, the binding class is mapped with its implementation
     * with an instance of the network service
     *
     * Remote data source is ready to be injected
     */
    bind<AuthorRemoteDataSource>() with singleton { AuthorRemoteDataSource(instance())  }
}