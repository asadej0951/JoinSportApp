package com.wewillapp.masterproject.utils.extension

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context.getDeviceMetrics(): DisplayMetrics {
    val metrics = DisplayMetrics()
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    display.getMetrics(metrics)
    return metrics
}