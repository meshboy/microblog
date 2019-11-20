package com.ex.microblog.core.mvvm

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */

/**
 * Define a base activity to be inherited by all activities in the app which already inherits
 * KodeinAware for the benefits of DI.
 */
abstract class BaseActivity<V : BaseView> : AppCompatActivity(), KodeinAware {

    /**
     * kodein object is retrieved by closestKodein. All injected dependencies can be retrieved
     * through KodeinAware instance which is supplied by closestKodein()
     */
    override val kodein by closestKodein()

    /**
     * All inherited classes must initialise the View shared between the activity and the viewmodel.
     */
    abstract fun createView(): V
}