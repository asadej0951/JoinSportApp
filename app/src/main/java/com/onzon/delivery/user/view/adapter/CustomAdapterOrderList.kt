package com.onzon.delivery.user.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.onzon.delivery.user.R
import com.onzon.delivery.user.databinding.ItemLoadMoreBinding
import com.onzon.delivery.user.databinding.ItemOrderListBinding
import com.onzon.delivery.user.vo.model.response.DataOrderList
import java.util.*

class CustomAdapterOrderList(
    private var context: Context,
    private var mListProduct: ArrayList<DataOrderList>,
    private var mOnClickList: (String) -> (Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_LOAD = 0
        private const val TYPE_LOAD_MORE = 1
    }

    override fun getItemCount(): Int {
        return mListProduct.size
    }

    override fun getItemViewType(position: Int): Int {
        return mListProduct[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewDataBinding
        return when (viewType) {
            TYPE_LOAD -> {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_order_list, parent, false)
                ViewHolder(binding as ItemOrderListBinding)
            }
            TYPE_LOAD_MORE -> {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_load_more, parent, false)
                ViewHolderLoadMore(
                    binding as ItemLoadMoreBinding
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding.dataViewModel = mListProduct[position]

            holder.binding.root.setOnClickListener {
                mOnClickList.invoke(mListProduct[position].id.toString())
            }
        }
    }

    class ViewHolderLoadMore(internal val binding: ItemLoadMoreBinding) : RecyclerView.ViewHolder(binding.root)

    class ViewHolder(internal var binding: ItemOrderListBinding) : RecyclerView.ViewHolder(binding.root)

}