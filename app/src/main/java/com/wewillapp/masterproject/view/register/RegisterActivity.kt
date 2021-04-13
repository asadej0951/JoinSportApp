package com.wewillapp.masterproject.view.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ActivityRegisterBinding
import com.wewillapp.masterproject.view.base.BaseActivity
import com.wewillapp.masterproject.vo.enumClass.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : BaseActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        toolbarViewModel.titleToolbarView.set(resources.getString(R.string.message_register))
        onSetFullScreenStatusBar()
    }

    private fun initViewModel() {
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
                        onStartAppIntent()
                    }
                }
                Status.ERROR -> mDialogPresenter.dialogMessage(
                    resources.getString(R.string.message_alert_dialog),
                    it.message
                ) {}
                Status.LOADING -> {
                }
            }
        })
    }

    private fun onClickListener() {
        viewModel.mLiveDataOnClickRegister.observe(this, Observer {
            when (it) {
                "addImageProfile" -> {
                    mCheckPermission.checkPermissionCameraAndStorage()
                }
                "selectStatus" -> {

                    val dataStatus = ArrayList<String>()
                    dataStatus.add("ผู้ใช้ทั่วไป")
                    dataStatus.add("ผู้ประกอบการ")

                    mDialogPresenter.dialogBottom(
                        dataStatus,
                        0,
                        resources.getString(R.string.message_select_status)
                    ) { s ->
                        binding.tvStatus.text = s
                        if (s == "ผู้ประกอบการ") {
                            binding.layoutDataOperate.visibility = View.VISIBLE
                            binding.etNumberPhone.imeOptions = EditorInfo.IME_ACTION_NEXT
                        } else {
                            binding.layoutDataOperate.visibility = View.GONE
                        }
                    }
                }
                "Register"->{
//                    var message = binding.etNumberPhone.text?.replace("[ /-]".toRegex(), "")
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        toolbarViewModel.onClickToolbar.observe(this, Observer {
            when (it) {
                "intentBack" -> {
                    this.onBackPressed()
                }
                else -> {
                    print("no event")
                }
            }
        })
    }

    private fun onStartAppIntent() {
        this.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && isCheckImageNull(
                requestCode, data
            ) && resultCode != 0
        ) {
            mCheckPermission.onSelectPicture(data, binding.ivProfile)
            viewModel.mLiveDataImageFile.value = mCheckPermission.getFile()
        }
    }

    private fun isCheckImageNull(requestCode: Int, data: Intent?): Boolean {
        return data != null && data.data != null && requestCode == 2 || requestCode == 1
    }

    override fun onBackPressed() {
        finish()
        startIntentAnimation(false)
    }
}
