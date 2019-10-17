package com.wewillapp.masterproject.utils.imageManagement

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wewillapp.masterproject.R
import javax.inject.Inject

class ImageViewUtils @Inject constructor() {

    @SuppressLint("CheckResult")
    fun setImageView(mContext:Context, urlImage:String,viewImage:ImageView){
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher_round)
        requestOptions.error(R.mipmap.ic_launcher_round)
        requestOptions.diskCacheStrategy
        requestOptions.fitCenter()

        Glide.with(mContext)
            .load(urlImage)
            .apply(requestOptions)
            .into(viewImage)
    }

    @SuppressLint("CheckResult")
    fun setImageViewRotate(mContext:Context, urlImage:String,viewImage:ImageView){
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher_round)
        requestOptions.error(R.mipmap.ic_launcher_round)
        requestOptions.diskCacheStrategy
        requestOptions.fitCenter()
        requestOptions.transform(RotateTransformation(90f))

        Glide.with(mContext)
            .load(urlImage)
            .apply(requestOptions)
            .into(viewImage)
    }

}