package com.wewillapp.masterproject.view.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.PagerAdapter
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.MainActivityBinding
import com.wewillapp.masterproject.view.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction().replace(
//                R.id.fragmentContainer,
//                MainFragment.newInstance()
//            ).commitNow()
//        }
        initView()
    }

    private fun initView() {
        onSetFullScreenStatusBar()
        val navController = findNavController(R.id.fragmentContainer)
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        mTokenExpiredDisposable.doCheckTokenExpire()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTokenExpiredDisposable.onDestroyDisposable()
    }
}
