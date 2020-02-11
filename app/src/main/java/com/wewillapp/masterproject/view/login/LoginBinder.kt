package com.wewillapp.masterproject.view.login

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityLoginBinding
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.vo.enumClass.Status
import javax.inject.Inject


interface SubScriptLoginBinder {
    fun onStartAppIntent(actionPage: String)
}

@SuppressLint("Registered")
open class LoginBinder : BaseActivity() {
    @Inject
    lateinit var viewModel: LoginViewModel

    lateinit var binding: ActivityLoginBinding

    private lateinit var subScriptLoginBinder: SubScriptLoginBinder

    private fun onSubScriptLoginBinder(mLogin:Any){
        subScriptLoginBinder = mLogin as SubScriptLoginBinder
    }

    fun onSubscriptViewModel() {
        onSubScriptLoginBinder(this)
        viewModel.mResponseLogin.observe(this, Observer {
            binding.loadResource = it
            when(it.status) {
                Status.SUCCESS -> {
                    mPreferences.saveToken(it.data!!.data.accessToken)
                    subScriptLoginBinder.onStartAppIntent("intentMain")
                }
                Status.ERROR -> mDialogPresenter.dialogMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
            }
        })

        viewModel.mOnClickListener.observe(this, Observer {
            subScriptLoginBinder.onStartAppIntent(it)
        })
    }


}