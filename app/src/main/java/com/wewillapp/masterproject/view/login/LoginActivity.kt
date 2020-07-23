package com.wewillapp.masterproject.view.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityLoginBinding
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.view.main.MainActivity
import com.wewillapp.masterproject.view.register.RegisterActivity
import com.wewillapp.masterproject.vo.enumClass.Status
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private lateinit var binding: ActivityLoginBinding

    private val mDialogPresenter: DialogPresenter by inject { parametersOf(binding.root.context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    }

    private fun initViewModel() {
        binding.handler = viewModel

        onSubscriptViewModel()
        onSubscriptOnClick()
    }

    private fun onSubscriptViewModel() {
        viewModel.mResponseLogin.observe(this, Observer {
            binding.loadResource = it
            when (it.status) {
                Status.SUCCESS -> {
                    mPreferences.saveToken(it.data!!.data.accessToken)
                    onStartAppIntent("intentMain")
                }
                Status.ERROR -> mDialogPresenter.dialogMessage(
                    resources.getString(R.string.message_alert_dialog),
                    it.message
                ) {}
                Status.LOADING -> {}
            }
        })
    }

    private fun onSubscriptOnClick() {
        viewModel.mOnClickListener.observe(this, Observer {
            onStartAppIntent(it)
        })
    }

    private fun onStartAppIntent(actionPage: String) {
        val intentApp: Intent
        when (actionPage) {
            "intentMain" -> {
                intentApp = Intent(this, MainActivity::class.java)
                startActivity(intentApp)
                finishAffinity()
            }
            "intentRegister" -> {
                intentApp = Intent(this, RegisterActivity::class.java)
                startActivity(intentApp)
            }
        }
        startIntentAnimation( true)
    }
}
