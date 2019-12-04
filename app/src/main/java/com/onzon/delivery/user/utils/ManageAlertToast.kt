package com.onzon.delivery.user.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.onzon.delivery.R

object ManageAlertToast {
    fun showToast(context: Context,view:View,message:String){
        val mSnackbar = Snackbar.make(view, message,
            Snackbar.LENGTH_LONG).setAction(context.resources.getString(R.string.message_done), null)
        mSnackbar.setActionTextColor(Color.WHITE)
        mSnackbar.show()
    }
}