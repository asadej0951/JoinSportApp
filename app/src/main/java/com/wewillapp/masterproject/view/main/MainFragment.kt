package com.wewillapp.masterproject.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


class MainFragment : BaseFragment() {

    private lateinit var binding:MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var mUtils: Utils

    lateinit var mCustomAdapterOrderList: CustomAdapterOrderList

    private var mListDataOrderList = ArrayList<DataOrderList>()

    @Inject
    lateinit var mDialogPresenter: DialogPresenter

    @Inject
    lateinit var mImageViewUtils: ImageViewUtils

    private var mLastPage = 0

    private var mCurrentPage = 1

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
            Toast.makeText(getBaseActivity(), it,Toast.LENGTH_LONG).show()
        }

        binding.recyclerViewOrderList.apply {
            setHasFixedSize(true)
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
                        viewModel.mOrderBookingCall.call()
                    }
                }
            }
        }
    }

    private fun onSubScriptViewModel() {
        mListDataOrderList.clear()
        viewModel.mOrderBookingCall.call()
        viewModel.mResponseOrderBooking.observe(this, Observer {
          binding.loadResource = it
                    when(it.status) {
                        Status.SUCCESS -> {
                            it.data!!.data.forEachIndexed { index, dataOrderList ->
                                dataOrderList.viewType = 0
                                mListDataOrderList.add(dataOrderList)
                            }

                            mLastPage = it.data.meta.total
                            mCustomAdapterOrderList.notifyDataSetChanged()
                        }
                        Status.ERROR -> mDialogPresenter.dialogAlertMessage(resources.getString(R.string.message_alert_dialog),it.message) {}
                    }
        })
    }

}
