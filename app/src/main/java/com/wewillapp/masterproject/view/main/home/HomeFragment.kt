package com.wewillapp.masterproject.view.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.FragmentHomeBinding
import com.wewillapp.masterproject.view.base.BaseFragment
import com.wewillapp.masterproject.vo.model.response.ResponsePost
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val mDataModelPost = ArrayList<ResponsePost>()

    private val mAdapterPost by lazy {
        AdapterPost(binding.root.context, mDataModelPost)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.dataViewModel = viewModel

        setData()
        initView()
        onClick()
    }

    private fun onClick() {
        viewModel.onClickListener.observe(this, Observer {
            var intent : Intent
            when(it){
                "PostMessage" -> {
                    intent = Intent(requireActivity(),PostActivity::class.java)
                    startActivity(intent)
                    mUtils.eventStartAnimationIntentTopBottom(requireActivity() as AppCompatActivity,true)
                }
                "Profile" ->{
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setData() {
        mDataModelPost.add(
            ResponsePost(
            "","","","","Hello"
        )
        )
        mDataModelPost.add(ResponsePost(
            "","","","xxxx","Hello"
        ))
        mDataModelPost.add(ResponsePost(
            "","","","","Hello"
        ))
        mDataModelPost.add(ResponsePost(
            "","","","xxxx","Hello"
        ))
        mDataModelPost.add(ResponsePost(
            "","","","","Hello"
        ))
    }

    private fun initView() {
        binding.recyclerViewPost.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mAdapterPost
            mAdapterPost.notifyDataSetChanged()
        }
    }


}