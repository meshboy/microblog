package com.ex.microblog.main.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ex.microblog.R
import com.ex.microblog.core.mvvm.BaseActivity
import com.ex.microblog.databinding.ActivityMainBinding
import com.ex.microblog.main.view.MainContainerView

class MainActivity : BaseActivity<MainContainerView>(), MainContainerView {

    /**
     * initialize the view used in this activity,
     */
    override fun createView(): MainContainerView = this

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//      set up navigation controller to hold toolbar and current fragment
        navController = this.findNavController(R.id.fragmentNavHost)
        NavigationUI.setupWithNavController(binding.toolbar, navController)
    }

    override fun showError(error: String?) {

    }
}
