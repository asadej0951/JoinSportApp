package com.wewillapp.masterproject.view.main.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityPostBinding
import com.wewillapp.masterproject.view.base.BaseActivity

class PostActivity : BaseActivity() {
    private lateinit var binding : ActivityPostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post)
        onSetFullScreenStatusBar()
        binding.toolbarViewModel = toolbarViewModel
        initView()
        onClick()
    }

    private fun onClick() {
        toolbarViewModel.onClickToolbar.observe(this, Observer {
            when (it) {
                "intentBack" -> {
                    this.onBackPressed()
                }
                "iconToolbar" ->{
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initView() {
        toolbarViewModel.titleToolbarView.set(resources.getString(R.string.message_post))
        toolbarViewModel.messageToolbar.set("โพสต์")
        binding.toolbar.layBackInvisible.visibility = View.VISIBLE
        binding.toolbar.imgBackInvisible.visibility = View.GONE
    }

    override fun onBackPressed() {
        finish()
        startIntentAnimationTopBottom(false)
        super.onBackPressed()
    }
}