package com.ex.microblog

import android.app.Application
import com.ex.microblog.core.di.modules.database.databaseModule
import com.ex.microblog.core.di.modules.network.networkModule
import com.ex.microblog.core.di.modules.repository.repositoryModule
import com.ex.microblog.core.di.modules.view.viewModule
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
        import(networkModule)
        import(repositoryModule)
        import(viewModule)
    }

    override fun onCreate() {
        super.onCreate()
        setUpTimber()
    }

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }
}