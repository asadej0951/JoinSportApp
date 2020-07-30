package com.wewillapp.masterproject.view.register

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.data.rest.useCase.GeneralUseCase
import com.wewillapp.masterproject.utils.SingleLiveData
import com.wewillapp.masterproject.utils.watcher.TextWatcherAdapter
import com.wewillapp.masterproject.vo.Resource
import com.wewillapp.masterproject.vo.model.body.BodyRegister
import com.wewillapp.masterproject.vo.model.response.BaseResponse
import java.io.File

class RegisterViewModel (generalUseCase: GeneralUseCase) : ViewModel() {

    val etEmail = ObservableField("")

    val etFullName = ObservableField("")

    val etPassword = ObservableField("")

    val etConfirmPass = ObservableField("")

    val isStatusButtonClick = ObservableField(false)

    val mLiveDataOnClickRegister = SingleLiveData<String>()

    val mLiveDataImageFile = MutableLiveData<File>()

    val onEmailTextChanged = TextWatcherAdapter { s ->
        etEmail.set(s)
        checkEventButtonClick()
    }

    val onFullNameTextChanged = TextWatcherAdapter { s ->
        etFullName.set(s)
        checkEventButtonClick()
    }

    val onPasswordTextChanged = TextWatcherAdapter { s ->
        etPassword.set(s)
        checkEventButtonClick()
    }
    val onConfirmPasswordTextChanged = TextWatcherAdapter { s ->
            etConfirmPass.set(s)
            checkEventButtonClick()
    }

    var mRegisterCall = SingleLiveData<Void>()
    val mResponseRegister: LiveData<Resource<BaseResponse>> = Transformations
        .switchMap(mRegisterCall) {
            generalUseCase.doRegister(postDataRegister(), mLiveDataImageFile.value)
        }

    private fun postDataRegister(): BodyRegister {
        return BodyRegister(
            etEmail.get()!!,
            etFullName.get()!!,
            etPassword.get()!!,
            etConfirmPass.get()!!
        )
    }

    fun onClickEventRegister() {
        mRegisterCall.call()
    }

    fun onClickEventAddImage() {
        mLiveDataOnClickRegister.value = "addImageProfile"
    }

    private fun checkEventButtonClick() {
        mLiveDataImageFile.value?.let {
            if (isCheckFieldEntry() && mLiveDataImageFile.value!!.exists()) {
                isStatusButtonClick.set(true)
            } else {
                isStatusButtonClick.set(false)
            }
        }
    }

    private fun isCheckFieldEntry(): Boolean {
        return etEmail.get()!!.isNotEmpty() &&
                etFullName.get()!!.isNotEmpty() &&
                etPassword.get()!!.isNotEmpty() && etConfirmPass.get()!!.isNotEmpty() &&
                etPassword.get()!! == etConfirmPass.get()
    }
}
