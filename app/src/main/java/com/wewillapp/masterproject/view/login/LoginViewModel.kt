package com.wewillapp.masterproject.view.login

import androidx.databinding.ObservableField
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.domain.GeneralUseCase
import com.wewillapp.masterproject.utils.SingleLiveData
import com.wewillapp.masterproject.utils.TextHelper
import com.wewillapp.masterproject.utils.watcher.TextWatcherAdapter
import com.wewillapp.masterproject.vo.model.body.BodyLogin

class LoginViewModel (private val generalUseCase: GeneralUseCase) : ViewModel() {

    val etUserName = ObservableField("gobank@gmail.com")

    val etPassWord = ObservableField("password")

    val isStatusButtonClick = ObservableField(false)

    var mLoginCall = SingleLiveData<BodyLogin>()

    var mOnClickListener = SingleLiveData<String>()

    val onUserNameTextChanged = TextWatcherAdapter { s ->
        etUserName.set(s)
        checkEventButtonClick()
    }

    val onPasswordTextChanged = TextWatcherAdapter { s ->
        etPassWord.set(s)
        checkEventButtonClick()
    }

    fun onClickLogin() {
//        mLoginCall.value = BodyLogin( etUserName.get()!!, etPassWord.get()!!,
//            "7C57196B27D826A2165F382821CF37C57196B27D826A2165F382821CF3", "th")
        mOnClickListener.value = "login"
    }

    fun onClickRegister() {
        mOnClickListener.value = "intentRegister"
    }

    val mResponseLogin = Transformations.switchMap(mLoginCall) {
        generalUseCase.doLogin(it)
    }

    fun checkEventButtonClick() {
        isStatusButtonClick.set(TextHelper.isNotEmptyStrings(etUserName.get()) && TextHelper.isNotEmptyStrings(etPassWord.get())
        )
    }
}
