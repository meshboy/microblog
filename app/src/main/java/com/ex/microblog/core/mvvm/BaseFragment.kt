package com.ex.microblog.core.mvvm

import androidx.fragment.app.Fragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */

/**
 * Define a base fragment to be inherited by all fragments in the app which already inherits
 * KodeinAware for the benefits of DI.
 */
abstract class BaseFragment<V : BaseView> : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    abstract fun createView(): V
}