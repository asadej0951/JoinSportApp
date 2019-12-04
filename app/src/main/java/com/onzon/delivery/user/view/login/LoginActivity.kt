package com.onzon.delivery.user.view.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.onzon.delivery.R
import com.onzon.delivery.user.view.main.MainActivity
import com.onzon.delivery.user.view.register.RegisterActivity

class LoginActivity : LoginBinder(),
    SubScriptLoginBinder {

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

    override fun onStartAppIntent(actionPage: String) {
        val intentApp: Intent
        when (actionPage){
            "intentMain" ->{
                intentApp = Intent(getBaseActivity, MainActivity::class.java)
                startActivity(intentApp)
                finishAffinity()
            }
            "intentRegister" -> {
                intentApp = Intent(getBaseActivity, RegisterActivity::class.java)
                startActivity(intentApp)
            }
        }
        mUtils.eventStartAnimationIntent(this,true)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
