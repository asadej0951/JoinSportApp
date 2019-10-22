package com.wewillapp.masterproject.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.view.adapter.CustomAdapterOrderList


class MainFragment : MainBinder() {

    companion object {
        fun newInstance(loadPage: String? = ""): MainFragment {
            val args = Bundle()
            args.putString("keyParam", loadPage)
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        binding.dataViewModel = viewModel

        initView()
        initViewModel()
    }
    private fun initView() {
        setTitleToolBar(binding.icView.tvTitle, resources.getString(R.string.app_name))
    }

    private fun initViewModel() {
        onClickListener()
        onSetDataOrderList()
        onSubScriptViewModel()
    }

    private fun onClickListener() {
        binding.icView.layBack.setOnClickListener {
            Toast.makeText(activity,"onClickBack",Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSetDataOrderList() {
        mCustomAdapterOrderList = CustomAdapterOrderList(getBaseActivity()!!,mListDataOrderList){
            Toast.makeText(getBaseActivity(), it, Toast.LENGTH_LONG).show()
        }

        binding.recyclerViewOrderList.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollListener())
            adapter = mCustomAdapterOrderList
            mCustomAdapterOrderList.notifyDataSetChanged()
        }
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                val linearLayoutManager = view.layoutManager as LinearLayoutManager?

                if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 1) {
                    if (mListDataOrderList.size != mLastPage) { // Check Load duplicate
                        mCurrentPage++
                        viewModel.mCurrentPage.set(mCurrentPage)
                        viewModel.mLayountLoadMore = true
                        mListDataOrderList[mListDataOrderList.lastIndex].viewType = 1
                        mCustomAdapterOrderList.notifyDataSetChanged()
                        viewModel.mOrderBookingCall.call()
                    }
                }
            }
        }
    }

}
