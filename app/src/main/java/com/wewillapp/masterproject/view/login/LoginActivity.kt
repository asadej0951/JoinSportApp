package com.wewillapp.masterproject.view.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityLoginBinding
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.view.main.MainActivity
import com.wewillapp.masterproject.vo.enumClass.Status
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

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

    private fun onSubscriptViewModel() {
        viewModel.mResponseLogin.observe(this, Observer {
          binding.loadResource = it
                    when(it.status) {
                        Status.SUCCESS -> {
                            startAppIntent("")
                        }
                        Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
                    }
        })
    }

    fun startAppIntent(actionPage:String) {
        val intentApp = Intent(getBaseActivity,MainActivity::class.java)
        startActivity(intentApp)
        finishAffinity()
    }

}
