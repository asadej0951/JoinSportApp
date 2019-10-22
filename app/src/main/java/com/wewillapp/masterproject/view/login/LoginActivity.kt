package com.wewillapp.masterproject.view.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.wewillapp.masterproject.R


class LoginActivity : LoginBinder() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.handler = viewModel

        onSubscriptViewModel()
    }

}
