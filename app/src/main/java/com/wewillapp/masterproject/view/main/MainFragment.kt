package com.wewillapp.masterproject.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.MainFragmentBinding
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import com.wewillapp.masterproject.utils.extension.showMessage
import com.wewillapp.masterproject.view.base.BaseFragment
import com.wewillapp.masterproject.vo.enumClass.Status
import com.wewillapp.masterproject.vo.model.response.DataOrderList
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment() {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by viewModel()

    private val mDialogPresenter: DialogPresenter by inject { parametersOf(binding.root.context) }

    private var mListDataOrderList = ArrayList<DataOrderList>()

    private val mCustomAdapterOrderList by lazy {
        AdapterOrderList(binding.root.context, mListDataOrderList, viewModel.onClickItemOrderList)
    }

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

        binding.dataViewModel = viewModel

        initView()
        initViewModel()
    }

    private fun initView() {

    }

    private fun initViewModel() {
        onClickListener()
        initOrderList()
        onSubScriptViewModel()
    }

    private fun onClickListener() {
        viewModel.onClickItemOrderList.observe(requireActivity(), Observer {
            binding.root.showMessage(it)
        })
    }

    private fun initOrderList() {
        binding.recyclerViewOrderList.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addOnScrollListener(onScrollListener())
            adapter = mCustomAdapterOrderList
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
                Status.LOADING -> {
                }
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
}
