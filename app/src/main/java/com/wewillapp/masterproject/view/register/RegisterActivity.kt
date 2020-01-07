package com.wewillapp.masterproject.view.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.wewillapp.masterproject.R


@SuppressLint("Registered")
class RegisterActivity : RegisterBinder(),
    SubScriptRegisterBinder {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setTitleToolBar(binding.icView.tvTitle, resources.getString(R.string.message_register))

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        binding.dataViewModel = viewModel

        onSubScriptViewModel()
        onClickListener()
    }

    private fun onClickListener() {
        binding.icView.imgBack.setOnClickListener {
            this.onBackPressed()
        }
    }

    override fun onStartAppIntent(param: String) {
        this.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null && requestCode == 2 || requestCode == 1) {
            if (resultCode != 0) {
                mCheckPermission.onSelectPicture(data, binding.ivProfile)
                viewModel.mLiveDataImageFile.value = mCheckPermission.getFile()
            }
        }
    }

    override fun onBackPressed() {
        finish()
        mUtils.eventStartAnimationIntent(this, false)
    }
}