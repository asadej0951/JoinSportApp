package com.wewillapp.masterproject.view.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.databinding.ItemPostMessageBinding
import com.wewillapp.masterproject.vo.model.response.ResponsePost

class AdapterPost(private val mContext: Context, private val mDataModel: ArrayList<ResponsePost>) :
    RecyclerView.Adapter<ViewHolderPost>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPost {
        val itemView: ItemPostMessageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_post_message, parent, false
        )
        return ViewHolderPost(itemView)
    }

    override fun getItemCount() = mDataModel.size

    override fun onBindViewHolder(holder: ViewHolderPost, position: Int) {
       holder.binding.tvMessagePost.text = mDataModel[position].message
        if (mDataModel[position].Image !=""){
            holder.binding.image.visibility = View.VISIBLE
        }else{
            holder.binding.image.visibility = View.GONE
        }
    }
}

class ViewHolderPost(internal val binding: ItemPostMessageBinding) :
    RecyclerView.ViewHolder(binding.root)