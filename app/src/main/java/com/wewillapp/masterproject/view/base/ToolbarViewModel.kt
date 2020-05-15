package com.wewillapp.masterproject.view.base

import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.utils.SingleLiveData
import javax.inject.Inject

class ToolbarViewModel @Inject
constructor() : ViewModel() {

    val onClickToolbar = SingleLiveData<String>()

    fun onClickBack(){
        onClickToolbar.value = "intentBack"
    }
}