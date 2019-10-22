package com.wewillapp.masterproject.view.main

import androidx.lifecycle.Observer
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.MainFragmentBinding
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import com.wewillapp.masterproject.utils.imageManagement.ImageViewUtils
import com.wewillapp.masterproject.view.adapter.CustomAdapterOrderList
import com.wewillapp.masterproject.view.base.BaseFragment
import com.wewillapp.masterproject.vo.enumClass.Status
import com.wewillapp.masterproject.vo.model.response.DataOrderList
import javax.inject.Inject

open class MainBinder:BaseFragment() {
    lateinit var binding: MainFragmentBinding

    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var mUtils: Utils

    lateinit var mCustomAdapterOrderList: CustomAdapterOrderList

    var mListDataOrderList = ArrayList<DataOrderList>()

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    @Inject
    lateinit var mImageViewUtils: ImageViewUtils

    var mLastPage = 0

    var mCurrentPage = 1

    fun onSubScriptViewModel() {
        mListDataOrderList.clear()
        viewModel.mOrderBookingCall.call()
        viewModel.mResponseOrderBooking.observe(this, Observer {
            binding.loadResource = it
            when(it.status) {
                Status.SUCCESS -> {
                    if (mListDataOrderList.isNotEmpty())
                        mListDataOrderList[mListDataOrderList.lastIndex].viewType = 0

                    viewModel.mLayountLoadMore = false
                    mListDataOrderList.addAll(it.data!!.data)
                    mLastPage = it.data.meta.total
                    mCustomAdapterOrderList.notifyDataSetChanged()
                }
                Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
            }
        })
    }
}