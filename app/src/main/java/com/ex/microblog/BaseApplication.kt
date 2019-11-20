package com.ex.microblog

import android.app.Application
import com.ex.microblog.core.di.modules.database.databaseModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */
class BaseApplication: Application(), KodeinAware  {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))
        import(databaseModule)
    }

    override fun onCreate() {
        super.onCreate()
        setUpTimber()
    }

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }
}