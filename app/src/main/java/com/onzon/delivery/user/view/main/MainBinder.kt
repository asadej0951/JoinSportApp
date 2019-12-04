package com.onzon.delivery.user.view.main

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onzon.delivery.R
import com.onzon.delivery.databinding.MainFragmentBinding
import com.onzon.delivery.user.utils.Utils
import com.onzon.delivery.user.utils.dialog.DialogPresenter
import com.onzon.delivery.user.utils.imageManagement.ImageViewUtils
import com.onzon.delivery.user.view.adapter.CustomAdapterOrderList
import com.onzon.delivery.user.view.base.BaseFragment
import com.onzon.delivery.user.vo.enumClass.Status
import com.onzon.delivery.user.vo.model.response.DataOrderList
import javax.inject.Inject

open class MainBinder : BaseFragment() {
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

    fun onSubScriptViewModel() {
        mListDataOrderList.clear()
        viewModel.mOrderBookingCall.call()
        viewModel.mResponseOrderBooking.observe(this, Observer {
            binding.loadResource = it
            when(it.status) {
                Status.SUCCESS -> {
                    if (mListDataOrderList.isNotEmpty())
                        mListDataOrderList[mListDataOrderList.lastIndex].viewType = 0

                    viewModel.mLayoutLoadMore = false
                    mListDataOrderList.addAll(it.data!!.data)
                    viewModel.mLastPage.set(it.data.meta.total)
                    mCustomAdapterOrderList.notifyDataSetChanged()
                }
                Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
            }
        })
    }

    fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                val linearLayoutManager = view.layoutManager as LinearLayoutManager?

                if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 1) {
                    if (mListDataOrderList.size != viewModel.mLastPage.get()) { // Check Load duplicate
                        viewModel.mCurrentPage.set(viewModel.mCurrentPage.get()!!+1)

                        viewModel.mLayoutLoadMore = true
                        mListDataOrderList[mListDataOrderList.lastIndex].viewType = 1
                        mCustomAdapterOrderList.notifyDataSetChanged()
                        viewModel.mOrderBookingCall.call()
                    }
                }
            }
        }
    }
}