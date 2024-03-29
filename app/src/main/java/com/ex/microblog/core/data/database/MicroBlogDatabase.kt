package com.ex.microblog.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ex.microblog.BuildConfig
import com.ex.microblog.core.data.author.entities.AuthorDao
import com.ex.microblog.core.data.author.entities.DatabaseAuthor
import com.ex.microblog.core.data.post.entities.DatabasePost
import com.ex.microblog.core.data.post.entities.PostDao

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * defines the database to cache data in the app
 */
@Database(
    entities = [DatabaseAuthor::class, DatabasePost::class],
    version = 1,
    exportSchema = true
)
abstract class MicroBlogDatabase : RoomDatabase() {

    abstract val authorDao: AuthorDao
    abstract val postDao: PostDao

    companion object {

        private val DATABASE_NAME = if (BuildConfig.DEBUG) "microBlogTest" else "mioroBlog"
        private lateinit var INSTANCE: MicroBlogDatabase

        /**
         * when the database is not created, build an instance of the database.
         * same instance is provided when shared across multiple objects. To be thread safe and ensure
         * a single instance is provided across the app, the instance creation needs to be synchronized
         */
        fun getDatabase(context: Context): MicroBlogDatabase {
            synchronized(MicroBlogDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = buildRoomDatabase(context)
                }
            }
            return INSTANCE
        }

        private fun buildRoomDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MicroBlogDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}