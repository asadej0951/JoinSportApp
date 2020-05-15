package com.wewillapp.masterproject.view.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityRegisterBinding
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.view.base.ToolbarViewModel
import com.wewillapp.masterproject.view.login.LoginViewModel
import com.wewillapp.masterproject.vo.enumClass.Status
import javax.inject.Inject


@SuppressLint("Registered")
class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: RegisterViewModel

    lateinit var binding: ActivityRegisterBinding

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
        viewModel =  ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.dataViewModel = viewModel
        binding.toolbarViewModel = toolbarViewModel

        onSubScriptViewModel()
        onClickListener()
    }

    private fun onSubScriptViewModel() {
        viewModel.mResponseRegister.observe(this, Observer {
            binding.loadResource = it
            when (it.status) {
                Status.SUCCESS -> {
                    mDialogPresenter.dialogMessage(
                        resources.getString(R.string.message_alert_dialog),
                        it.message
                    ) {
                        onStartAppIntent("intentMain")
                    }
                }
                Status.ERROR -> mDialogPresenter.dialogMessage(
                    resources.getString(R.string.message_alert_dialog),
                    it.message
                ) {}
            }
        })
    }


    private fun onClickListener() {
        viewModel.mLiveDataOnClickRegister.observe(this, Observer {
            when (it) {
                "addImageProfile" -> {
                    mCheckPermission.checkPermissionCameraAndStorage()
                }
            }
        })

        toolbarViewModel.onClickToolbar.observe(this, Observer {
            this.onBackPressed()
        })
    }

    private fun onStartAppIntent(param: String) {
        this.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && isCheckImageNull(requestCode, data) && resultCode != 0) {
            mCheckPermission.onSelectPicture(data, binding.ivProfile)
            viewModel.mLiveDataImageFile.value = mCheckPermission.getFile()
        }
    }

    private fun isCheckImageNull(requestCode: Int, data: Intent?): Boolean {
        return data != null && data.data != null && requestCode == 2 || requestCode == 1
    }

    override fun onBackPressed() {
        finish()
        mUtils.eventStartAnimationIntent(this, false)
    }
}