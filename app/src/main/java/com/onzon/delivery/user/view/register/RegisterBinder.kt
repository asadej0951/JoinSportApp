package com.onzon.delivery.user.view.register

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.onzon.delivery.R
import com.onzon.delivery.databinding.ActivityRegisterBinding
import com.onzon.delivery.user.view.base.BaseActivity
import com.onzon.delivery.user.vo.enumClass.Status
import javax.inject.Inject

interface SubScriptRegisterBinder{
    fun onStartAppIntent(param:String)
}
@SuppressLint("Registered")
open class RegisterBinder : BaseActivity() {

    @Inject
    lateinit var viewModel: RegisterViewModel

    lateinit var binding: ActivityRegisterBinding

    lateinit var mSubScriptRegisterBinder: SubScriptRegisterBinder

    fun onSubScriptRegisterBinder(objects:Any){
        mSubScriptRegisterBinder = objects as SubScriptRegisterBinder
    }

    fun onSubScriptViewModel() {
        onSubScriptRegisterBinder(this)
        viewModel.mLiveDataOnClickRegister.observe(this, Observer {
            when (it) {
                "addImageProfile" -> {
                    mCheckPermission.checkPermissionCameraAndStorage()
                }
            }
        })

        viewModel.mResponseRegister.observe(this, Observer {
          binding.loadResource = it
                    when(it.status) {
                        Status.SUCCESS -> {
                            mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {
                                mSubScriptRegisterBinder.onStartAppIntent("intentMain")
                            }
                        }
                        Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
                    }
        })
    }

}