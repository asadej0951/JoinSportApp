package com.wewillapp.masterproject.view.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.MainActivityBinding
import com.wewillapp.masterproject.view.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainer,
                MainFragment.newInstance()
            ).commitNow()
        }

        initView()
        initViewModel()
    }

    private fun initView() {
        toolbarViewModel.titleToolbarView.set(resources.getString(R.string.app_name))
    }

    private fun initViewModel() {
        binding.toolbarViewModel = toolbarViewModel
        onClickListener()
    }


    private fun onClickListener() {
        toolbarViewModel.onClickToolbar.observe(this, Observer {
            when (it) {
                "intentBack" -> {
                   this.onBackPressed()
                }
                else -> {
                    print("no event")
                }
            }
        })

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
