package com.wewillapp.masterproject.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ItemOrderListBinding
import com.wewillapp.masterproject.vo.model.response.DataOrderList
import java.util.*

class CustomAdapterOrderList(
    private var context: Context,
    private var mListProduct: ArrayList<DataOrderList>,
    private var mOnClickList: (String) -> (Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return mListProduct.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: ItemOrderListBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_order_list, parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding.dataViewModel = mListProduct[position]

            holder.binding.root.setOnClickListener {
                mOnClickList.invoke(mListProduct[position].id.toString())
            }
        }
    }


    class ViewHolder(internal var binding: ItemOrderListBinding) : RecyclerView.ViewHolder(binding.root)

}