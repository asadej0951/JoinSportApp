package com.wewillapp.masterproject.view.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.utils.SingleLiveData
import javax.inject.Inject

class ToolbarViewModel : ViewModel() {

    val onClickToolbar = SingleLiveData<String>()

    val titleToolbarView = ObservableField("")

    val messageToolbar = ObservableField("")

    fun onClickBack() {
        onClickToolbar.value = "intentBack"
    }
    fun onClickIconToolbar(){
        onClickToolbar.value = "iconToolbar"
    }
}
