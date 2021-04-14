package com.wewillapp.masterproject.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wewillapp.masterproject.R
import com.wewillapp.masterproject.data.local.Preferences
import qiu.niorgai.StatusBarCompat

class Utils constructor(
    private val context: Context,
    private val mPreferences: Preferences
) {

    fun setImageAutoMetrics(context: Context, imageView: ImageView) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width: Int = displayMetrics.widthPixels
        imageView.layoutParams.height = width / 3
    }

    fun setImageAutoMetrics(context: Context, imageView: View, number: Int) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width: Int = displayMetrics.widthPixels
        imageView.layoutParams.height = width / number
    }

    fun eventStartAnimationIntent(activity: AppCompatActivity, isCheck: Boolean) {
        if (isCheck) {
            activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        } else {
            activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }
    }
    fun eventStartAnimationIntentTopBottom(activity: AppCompatActivity, isCheck: Boolean) {
        if (isCheck) {
            activity.overridePendingTransition(R.anim.trans_top_in, R.anim.trans_top_out)
        } else {
            activity.overridePendingTransition(R.anim.trans_bottom_in, R.anim.trans_bottom_out)
        }
    }

    fun closeKeyborad(context: AppCompatActivity, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun onSetStatusBar(context: Context, colorStatusBar: Boolean) {
        if (colorStatusBar)
            StatusBarCompat.setStatusBarColor(
                context as Activity,
                ContextCompat.getColor(context, R.color.textColorWhite)
            )
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                StatusBarCompat.translucentStatusBar(context as Activity, true)
            else
                StatusBarCompat.translucentStatusBar(context as Activity)
        }
    }

    fun showBadge(
        context: Context,
        bottomNavigationView: BottomNavigationView,
        @IdRes itemId: Int,
        value: String
    ) {
        val valueBadge: String

        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        val badge = LayoutInflater.from(context)
            .inflate(R.layout.notification_badge, bottomNavigationView, false)
        val text = badge.findViewById<TextView>(R.id.notifications)

        if (value != "0" && value.isNotEmpty()) {
            valueBadge = if (value.toInt() > 999) {
                "999+"
            } else {
                value
            }
            text.text = valueBadge
            itemView.addView(badge)
        } else {
            removeBadge(bottomNavigationView, itemId)
        }
    }

    private fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        if (itemView.childCount == 3) { // position menu
            itemView.removeViewAt(2)
        }
    }
}
