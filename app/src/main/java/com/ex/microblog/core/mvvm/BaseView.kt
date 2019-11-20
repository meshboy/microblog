package com.ex.microblog.core.mvvm

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */

/**
 * Spec that serves as a base for any View used by the custom viewModel , activity or fragment
 */
interface BaseView {
    fun showError(error: String?)
}