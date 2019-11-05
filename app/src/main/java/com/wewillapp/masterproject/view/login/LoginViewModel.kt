package com.wewillapp.masterproject.view.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.data.Constants
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.utils.SingleLiveData
import com.wewillapp.masterproject.utils.TextHelper
import com.wewillapp.masterproject.utils.watcher.TextWatcherAdapter
import com.wewillapp.masterproject.vo.Resource
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin
import javax.inject.Inject


class LoginViewModel @Inject
constructor(private val generalRepository: GeneralRepository) : ViewModel() {

    val etUserName = ObservableField<String>(if (Constants.MODE_DEBUG) "gobank@gmail.com" else "")

    val etPassWord = ObservableField<String>(if (Constants.MODE_DEBUG) "password" else "")

    val isStatusButtonClick = ObservableField<Boolean>(false)

    val onUserNameTextChanged = TextWatcherAdapter{ s ->
        etUserName.set(s)
        checkEventButtonClick()
    }

    val onPasswordTextChanged = TextWatcherAdapter{ s ->
        etPassWord.set(s)
        checkEventButtonClick()
    }

    fun onClickLogin(){
       mCallLogin.call()
    }

    var mCallLogin = SingleLiveData<Void>()
    val mResponseLogin : LiveData<Resource<ResponseLogin>> = Transformations.switchMap(mCallLogin) {
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