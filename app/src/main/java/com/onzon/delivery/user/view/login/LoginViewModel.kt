package com.onzon.delivery.user.view.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.onzon.delivery.user.data.Constants
import com.onzon.delivery.user.data.rest.repository.GeneralRepository
import com.onzon.delivery.user.utils.SingleLiveData
import com.onzon.delivery.user.utils.TextHelper
import com.onzon.delivery.user.utils.watcher.TextWatcherAdapter
import com.onzon.delivery.user.vo.Resource
import com.onzon.delivery.user.vo.model.body.BodyLogin
import com.onzon.delivery.user.vo.model.response.ResponseLogin
import javax.inject.Inject


class LoginViewModel @Inject
constructor(private val generalRepository: GeneralRepository) : ViewModel() {

    val etUserName = ObservableField<String>(if (Constants.MODE_DEBUG) "gobank@gmail.com" else "")

    val etPassWord = ObservableField<String>(if (Constants.MODE_DEBUG) "password" else "")

    val isStatusButtonClick = ObservableField<Boolean>(false)

    var mLoginCall = SingleLiveData<Void>()

    var mOnClickListener = SingleLiveData<String>()

    val onUserNameTextChanged = TextWatcherAdapter { s ->
        etUserName.set(s)
        checkEventButtonClick()
    }

    val onPasswordTextChanged = TextWatcherAdapter { s ->
        etPassWord.set(s)
        checkEventButtonClick()
    }

    fun onClickLogin(){
        mLoginCall.call()
    }

    fun onClickRegister(){
        mOnClickListener.value = "intentRegister"
    }

    val mResponseLogin : LiveData<Resource<ResponseLogin>> = Transformations.switchMap(mLoginCall) {
        generalRepository.onLogin(
            BodyLogin(
                etUserName.get()!!, etPassWord.get()!!
                , "7C57196B27D826A2165F382821CF37C57196B27D826A2165F382821CF3", "th"
            )
        )
    }


    fun checkEventButtonClick() {
        if (TextHelper.isNotEmptyStrings(etUserName.get()) && TextHelper.isNotEmptyStrings(etPassWord.get()))
            isStatusButtonClick.set(true)
        else
            isStatusButtonClick.set(false)
    }

}