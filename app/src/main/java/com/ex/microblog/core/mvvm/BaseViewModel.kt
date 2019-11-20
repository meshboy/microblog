package com.ex.microblog.core.mvvm

import androidx.lifecycle.ViewModel

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */

/**
 * This class serves as a base for all ViewModels, the ViewModel is used as the parent class other than
 * AndroidViewModel because the context for the viewModel or fragment or activity is served in the
 * BaseApplication via DI (androidXModule)
 */
abstract class BaseViewModel<V : BaseView> : ViewModel() {

    /**
     * Customs viewmodel inheriting this class will have direct access to the view passed in
     * as a type. This view is expected to be initialised in either a fragment or activity.
     */
    open lateinit var view: V
}