package com.onzon.delivery.user.view.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.onzon.delivery.user.data.rest.repository.GeneralRepository
import com.onzon.delivery.user.utils.SingleLiveData
import com.onzon.delivery.user.vo.Resource
import com.onzon.delivery.user.vo.model.response.ResponseOrderList
import javax.inject.Inject

class MainViewModel @Inject constructor(generalRepository: GeneralRepository) : ViewModel() {
    val mCurrentPage = ObservableField(1)

    val mLastPage = ObservableField(1)

    var mLayoutLoadMore = false

    val mOrderBookingCall = SingleLiveData<Void>()
    val mResponseOrderBooking : LiveData<Resource<ResponseOrderList>> = Transformations.switchMap(mOrderBookingCall){
        generalRepository.getOrderList(mCurrentPage.get()!!)
    }

}
