package com.wewillapp.masterproject.view.main

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.utils.SingleLiveData

class MainViewModel @ViewModelInject constructor(generalRepository: GeneralRepository) : ViewModel() {
    val mCurrentPage = ObservableField(1)

    val isLoadDuplicate = ObservableField(true)

    val onClickItemOrderList = SingleLiveData<String>()

    val mOrderBookingCall = SingleLiveData<Void>()
    val mResponseOrderBooking = Transformations.switchMap(mOrderBookingCall) {
        generalRepository.getOrderList(mCurrentPage.get()!!)
    }
}
