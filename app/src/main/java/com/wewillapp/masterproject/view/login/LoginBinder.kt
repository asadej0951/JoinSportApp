package com.wewillapp.masterproject.view.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityLoginBinding
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.view.main.MainActivity
import com.wewillapp.masterproject.vo.enumClass.Status
import javax.inject.Inject

@SuppressLint("Registered")
open class LoginBinder : BaseActivity() {
    @Inject
    lateinit var viewModel: LoginViewModel

    lateinit var binding: ActivityLoginBinding

    fun onSubscriptViewModel() {
        viewModel.mResponseLogin.observe(this, Observer {
            binding.loadResource = it
            when(it.status) {
                Status.SUCCESS -> {
                    mPreferences.saveToken(it.data!!.data.accessToken)
                    startAppIntent("")
                }
                Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
            }
        })
    }

    private fun startAppIntent(actionPage:String) {
        val intentApp: Intent
        when (actionPage){
            "" ->{
                intentApp = Intent(getBaseActivity, MainActivity::class.java)
                startActivity(intentApp)
                finishAffinity()
                mUtils.eventStartAnimationIntent(this,true)
            }
        }

    }
}