package com.wewillapp.masterproject.view.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.data.Constants
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.util.SingleLiveData
import com.wewillapp.masterproject.util.watcher.TextWatcherAdapter
import com.wewillapp.masterproject.vo.Resource
import com.wewillapp.masterproject.vo.model.body.BodyLogin
import com.wewillapp.masterproject.vo.model.response.ResponseLogin


class LoginViewModel @Inject
constructor(private val generalRepository: GeneralRepository) : ViewModel() {

    val etUserName = ObservableField<String>(if (Constants.MODE_DEBUG) "gobank@gmail.com" else "")

    val etPassWord = ObservableField<String>(if (Constants.MODE_DEBUG) "password" else "")

    val onUserNameTextChanged = TextWatcherAdapter{ s ->
        etUserName.set(s)
    }

    val onPasswordTextChanged = TextWatcherAdapter{ s ->
        etPassWord.set(s)
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


}