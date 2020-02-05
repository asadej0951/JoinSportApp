package com.wewillapp.masterproject.utils.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wewillapp.masterproject.R


object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value=["visibleGone", "visibleGoneLoadMore"], requireAll=false)
    fun showHide(view: View, visibleGone: Boolean,visibleGoneLoadMore:Boolean) {
        if (!visibleGoneLoadMore) {
            view.visibility = if (visibleGone) View.VISIBLE else View.GONE
        }else{
            view.visibility = View.GONE
        }
    }


    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun viewImage(view: ImageView, url: String? = "") {

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher_round)
        requestOptions.error(R.mipmap.ic_launcher_round)
        requestOptions.diskCacheStrategy
        requestOptions.centerCrop()

        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(view)
    }


    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun viewImage(view: ImageView, url: Int) {

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher_round)
        requestOptions.error(R.mipmap.ic_launcher_round)
        requestOptions.diskCacheStrategy
        requestOptions.centerCrop()

        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(view)
    }
}