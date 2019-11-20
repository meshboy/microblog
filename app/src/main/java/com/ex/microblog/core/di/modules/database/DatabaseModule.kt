package com.ex.microblog.core.di.modules.database

import com.ex.microblog.core.data.author.entities.AuthorDao
import com.ex.microblog.core.data.author.repository.AuthorLocalDataSource
import com.ex.microblog.core.data.database.MicroBlogDatabase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * DatabaseModule holds all database bindings. All bindings interfaces are directly mapped to
 * their implementations
 */
val databaseModule = Kodein.Module("Database Module") {

    /**
     * Getting an instance of the database should only happen once using singleton from kodein,
     * any other request should be served the same instance firstly created.
     *
     * MicroBlogDatabase is ready to be injected
     */
    bind<MicroBlogDatabase>() with singleton { MicroBlogDatabase.getDatabase(instance()) }

    /**
     * binds with authorDao from the database.
     */
    bind<AuthorDao>() with singleton { MicroBlogDatabase.getDatabase(instance()).authorDao }

    /**
     * The binded dao is provided via an instance of Kodein. The resulting implementation
     * is binded to the authorLocalDataSource
     *
     * local data source is ready to be injected
     */
    bind<AuthorLocalDataSource>() with singleton { AuthorLocalDataSource(instance()) }
}