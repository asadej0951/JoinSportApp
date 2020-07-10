package com.wewillapp.masterproject.utils.imageManagement

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wewillapp.masterproject.R

fun ImageView.setImageView(mContext: Context, urlImage: String) {
    Glide.with(mContext)
        .load(urlImage)
        .placeholder(R.mipmap.ic_launcher_round)
        .error(R.mipmap.ic_launcher_round)
        .fitCenter()
        .into(this)
}

fun ImageView.setImageViewRotate(mContext: Context, urlImage: String) {
    Glide.with(mContext)
        .load(urlImage)
        .placeholder(R.mipmap.ic_launcher_round)
        .error(R.mipmap.ic_launcher_round)
        .fitCenter()
        .transform(RotateTransformation(90f))
        .into(this)
}
