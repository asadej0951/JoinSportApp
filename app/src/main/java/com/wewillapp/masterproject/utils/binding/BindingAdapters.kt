package com.wewillapp.masterproject.utils.binding

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wewillapp.masterproject.R


object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun viewImage(view: ImageView, url: String? = "") {

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher_round)
        requestOptions.error(R.mipmap.ic_launcher_round)
        requestOptions.diskCacheStrategy
        requestOptions.fitCenter()

        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(view)
    }
}