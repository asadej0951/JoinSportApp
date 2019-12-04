package com.onzon.delivery.user.view.login

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.onzon.delivery.R
import com.onzon.delivery.databinding.ActivityLoginBinding
import com.onzon.delivery.user.view.base.BaseActivity
import com.onzon.delivery.user.vo.enumClass.Status
import javax.inject.Inject

interface SubScriptLoginBinder {
    fun onStartAppIntent(actionPage: String)
}

@SuppressLint("Registered")
open class LoginBinder : BaseActivity() {
    @Inject
    lateinit var viewModel: LoginViewModel

    lateinit var binding: ActivityLoginBinding

    lateinit var subScriptLoginBinder: SubScriptLoginBinder

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
                Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
            }
        })

        viewModel.mOnClickListener.observe(this, Observer {
            subScriptLoginBinder.onStartAppIntent(it)
        })
    }


}