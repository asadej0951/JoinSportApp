package com.wewillapp.masterproject.view.main

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.MainFragmentBinding
import com.wewillapp.masterproject.view.adapter.CustomAdapterOrderList
import com.wewillapp.masterproject.view.base.BaseFragment
import com.wewillapp.masterproject.vo.enumClass.Status
import com.wewillapp.masterproject.vo.model.response.DataOrderList

open class MainBinder : BaseFragment() {
    lateinit var binding: MainFragmentBinding

    lateinit var viewModel: MainViewModel

    lateinit var mCustomAdapterOrderList: CustomAdapterOrderList

    var mListDataOrderList = ArrayList<DataOrderList>()

    @SuppressLint("FragmentLiveDataObserve")
    fun onSubScriptViewModel() {
        mListDataOrderList.clear()
        viewModel.mOrderBookingCall.call()
        viewModel.mResponseOrderBooking.observe(this, Observer {
            binding.loadResource = it
            when(it.status) {
                Status.SUCCESS -> {
                    if (mListDataOrderList.isNotEmpty())
                        mListDataOrderList[mListDataOrderList.lastIndex].viewType = 0

                    mListDataOrderList.addAll(it.data!!.data)
                    viewModel.mLastPage.set(it.data.meta.total)
                    mCustomAdapterOrderList.notifyDataSetChanged()
                }
                Status.ERROR -> mDialogPresenter.dialogMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
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

                        mListDataOrderList[mListDataOrderList.lastIndex].viewType = 1
                        mCustomAdapterOrderList.notifyDataSetChanged()
                        viewModel.mOrderBookingCall.call()
                    }
                }
            }
        }
    }
}