package com.wewillapp.masterproject.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ItemOrderListBinding
import com.wewillapp.masterproject.utils.SingleLiveData
import com.wewillapp.masterproject.utils.imageManagement.setImageView
import com.wewillapp.masterproject.vo.model.response.DataOrderList
import java.util.*

class AdapterOrderList(
    private var mContext: Context,
    private var mListProduct: ArrayList<DataOrderList>,
    private var mOnClickList: SingleLiveData<String>
) : RecyclerView.Adapter<AdapterOrderList.ViewHolder>() {

    override fun getItemCount(): Int {
        return mListProduct.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_order_list, parent, false
        ) as ItemOrderListBinding

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindText(holder, position)

        holder.binding.root.setOnClickListener {
            mOnClickList.value = mListProduct[position].bookingUsername
        }
    }

    private fun bindText(holder: ViewHolder, position: Int) {
        holder.binding.ivCircleOut.setImageView(mContext, "")
        holder.binding.tvOrderName.text = mListProduct[position].bookingUsername
    }

    class ViewHolder(internal var binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)
}
