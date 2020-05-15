package com.wewillapp.masterproject.view.login

import androidx.databinding.ObservableField
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.utils.SingleLiveData
import com.wewillapp.masterproject.utils.TextHelper
import com.wewillapp.masterproject.utils.watcher.TextWatcherAdapter
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import javax.inject.Inject


class LoginViewModel @Inject
constructor(private val generalRepository: GeneralRepository) : ViewModel() {

    val etUserName = ObservableField("gobank@gmail.com")

    val etPassWord = ObservableField("password")

    val isStatusButtonClick = ObservableField(false)

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

    fun onClickLogin() {
        mLoginCall.call()
    }

    fun onClickRegister() {
        mOnClickListener.value = "intentRegister"
    }

    val mResponseLogin = Transformations.switchMap(mLoginCall) {
        generalRepository.onLogin(
            BodyLogin(
                etUserName.get()!!, etPassWord.get()!!
                , "7C57196B27D826A2165F382821CF37C57196B27D826A2165F382821CF3", "th"
            )
        )
    }


    fun checkEventButtonClick() {
        if (TextHelper.isNotEmptyStrings(etUserName.get()) && TextHelper.isNotEmptyStrings(
                etPassWord.get()
            )
        )
            isStatusButtonClick.set(true)
        else
            isStatusButtonClick.set(false)
    }

}