package com.wewillapp.masterproject.view.main.home

import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.utils.SingleLiveData

class HomeViewModel:ViewModel(){


    val onClickListener = SingleLiveData<String>()

    fun onClickPostMessage(){
        onClickListener.value = "PostMessage"
    }
    fun onClickProfile(){
        onClickListener.value = "Profile"
    }
}