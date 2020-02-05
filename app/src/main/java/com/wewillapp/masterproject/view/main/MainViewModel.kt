package com.wewillapp.masterproject.view.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.utils.SingleLiveData
import com.wewillapp.masterproject.vo.Resource
import com.wewillapp.masterproject.vo.model.response.ResponseOrderList
import javax.inject.Inject

class MainViewModel @Inject constructor(generalRepository: GeneralRepository) : ViewModel() {
    val mCurrentPage = ObservableField(1)

    val mLastPage = ObservableField(1)

    val mOrderBookingCall = SingleLiveData<Void>()
    val mResponseOrderBooking : LiveData<Resource<ResponseOrderList>> = Transformations.switchMap(mOrderBookingCall){
        generalRepository.getOrderList(mCurrentPage.get()!!)
    }

}
