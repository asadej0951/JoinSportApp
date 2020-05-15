package com.wewillapp.masterproject.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.MainFragmentBinding
import com.wewillapp.masterproject.view.base.BaseFragment
import com.wewillapp.masterproject.vo.enumClass.Status
import com.wewillapp.masterproject.vo.model.response.DataOrderList

class MainFragment : BaseFragment() {

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var mCustomAdapterOrderList: AdapterOrderList

    private var mListDataOrderList = ArrayList<DataOrderList>()

    companion object {
        fun newInstance(loadPage: String? = ""): MainFragment {
            val args = Bundle()
            args.putString("keyParam", loadPage)
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.dataViewModel = viewModel
        binding.toolbarViewModel = toolbarViewModel

        initView()
        initViewModel()
    }

    private fun initView() {
        setTitleToolBar(binding.icView.tvTitle, resources.getString(R.string.app_name))
    }

    private fun initViewModel() {
        mTokenExpiredDisposable.doCheckTokenExpire()
        onClickListener()
        onSetDataOrderList()
        onSubScriptViewModel()
    }

    private fun onClickListener() {
        toolbarViewModel.onClickToolbar.observe(requireActivity(), Observer {
            Toast.makeText(activity, "onClickBack", Toast.LENGTH_SHORT).show()
        })

        viewModel.onClickItemList.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun onSetDataOrderList() {
        mCustomAdapterOrderList =
            AdapterOrderList(mListDataOrderList, viewModel.onClickItemList)

        binding.recyclerViewOrderList.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addOnScrollListener(onScrollListener())
            adapter = mCustomAdapterOrderList
            mCustomAdapterOrderList.notifyDataSetChanged()
        }
    }


    private fun onSubScriptViewModel() {
        viewModel.mOrderBookingCall.call()
        viewModel.mResponseOrderBooking.observe(requireActivity(), Observer {
            binding.loadResource = it
            when (it.status) {
                Status.SUCCESS -> {
                    mListDataOrderList.addAll(it.data!!.data)
                    viewModel.isLoadDuplicate.set(!it.data.links.next.isNullOrBlank())
                    mCustomAdapterOrderList.notifyDataSetChanged()
                }
                Status.ERROR -> mDialogPresenter.dialogMessage(
                    resources.getString(R.string.message_alert_dialog),
                    it.message
                ) {}
            }
        })
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                val linearLayoutManager = view.layoutManager as LinearLayoutManager?

                if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 1 && viewModel.isLoadDuplicate.get()!!) {
                    viewModel.mCurrentPage.set(viewModel.mCurrentPage.get()!! + 1)
                    mCustomAdapterOrderList.notifyDataSetChanged()
                    viewModel.mOrderBookingCall.call()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTokenExpiredDisposable.onDestroyDisposable()
    }

}
