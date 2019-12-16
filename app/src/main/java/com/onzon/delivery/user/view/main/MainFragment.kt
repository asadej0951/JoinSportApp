package com.onzon.delivery.user.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.onzon.delivery.user.R
import com.onzon.delivery.user.view.adapter.CustomAdapterOrderList

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
        mCustomAdapterOrderList = CustomAdapterOrderList(
            getBaseActivity()!!,
            mListDataOrderList
        ) {
            Toast.makeText(getBaseActivity(), it, Toast.LENGTH_LONG).show()
        }

        binding.recyclerViewOrderList.apply {
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollListener())
            adapter = mCustomAdapterOrderList
            mCustomAdapterOrderList.notifyDataSetChanged()
        }
    }

}
