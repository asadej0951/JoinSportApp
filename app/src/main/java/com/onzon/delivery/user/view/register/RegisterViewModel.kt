package com.onzon.delivery.user.view.register

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.onzon.delivery.user.data.rest.repository.GeneralRepository
import com.onzon.delivery.user.utils.SingleLiveData
import com.onzon.delivery.user.utils.watcher.TextWatcherAdapter
import com.onzon.delivery.user.vo.Resource
import com.onzon.delivery.user.vo.model.body.BodyRegister
import com.onzon.delivery.user.vo.model.response.BaseResponse
import java.io.File
import javax.inject.Inject

class RegisterViewModel
@Inject constructor(generalRepository: GeneralRepository) : ViewModel() {

    val etEmail = ObservableField<String>("")
    val etFullName = ObservableField<String>("")
    val etPassword = ObservableField<String>("")
    val etConfirmPass = ObservableField<String>("")

    val isStatusButtonClick =  ObservableField<Boolean>(false)

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
    val onConfirmPasswordTextChanged =
        TextWatcherAdapter { s ->
            etConfirmPass.set(s)
            checkEventButtonClick()
        }

    var mRegisterCall = SingleLiveData<Void>()
    val mResponseRegister: LiveData<Resource<BaseResponse>> = Transformations
        .switchMap(mRegisterCall) {
            generalRepository.onRegister(
                BodyRegister(
                    etEmail.get()!!,
                    etFullName.get()!!,
                    etPassword.get()!!,
                    etConfirmPass.get()!!
                ), mLiveDataImageFile.value
            )
        }

    fun onClickEventRegister() {
        mRegisterCall.call()
    }


    fun onClickEventAddImage() {
        mLiveDataOnClickRegister.value = "addImageProfile"
    }

    fun checkEventButtonClick() {
        mLiveDataImageFile.value?.let {
            if (etEmail.get()!!.isNotEmpty()
                && etFullName.get()!!.isNotEmpty()
                && etPassword.get()!!.isNotEmpty() && etConfirmPass.get()!!.isNotEmpty()
                && etPassword.get()!! == etConfirmPass.get() && mLiveDataImageFile.value!!.exists()){
                isStatusButtonClick.set(true)
            }else{
                isStatusButtonClick.set(false)
            }
        }
    }

}