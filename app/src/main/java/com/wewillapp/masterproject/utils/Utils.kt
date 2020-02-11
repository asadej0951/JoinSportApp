package com.wewillapp.masterproject.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wewillapp.masterproject.R
import qiu.niorgai.StatusBarCompat
import java.util.*
import javax.inject.Inject

class Utils @Inject constructor() {

    @SuppressLint("DefaultLocale")
    fun setDefaultLanguage(context:Context, language:String){
        val config = Configuration()
        config.locale = Locale(language.toLowerCase())
        context.resources.updateConfiguration(config, null)
    }


    fun setImageAutoMetrics(context: Context,imageView: ImageView){
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width: Int = displayMetrics.widthPixels
        imageView.layoutParams.height = width / 3
    }

    fun eventStartAnimationIntent(activity: AppCompatActivity, isCheck: Boolean) {
        if (isCheck) {
            activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        } else {
            activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }
    }


    fun getRandomString() : Int {
        val ranDomNumber = (0..10).random()
        val ranDomNumber2 = (0..99).random()
        return (ranDomNumber * ranDomNumber2) * 1000000000
    }


    fun closeKeyborad(context: AppCompatActivity,view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setFormatNumber(number:Double):String{
        val mResult: String
        val mCheckDecimal = number.toString().split(".")
        mResult = if (mCheckDecimal.isNotEmpty()){
            if (mCheckDecimal[1].toDouble() > 0.0){
                String.format("%,.2f", number)
            }else{
                String.format("%,.0f", number)
            }
        }else{
            String.format("%,.2f", number)
        }
        return mResult
    }

    fun onSetStatusBar(context: Context, colorStatusBar: Boolean) {
        if (colorStatusBar)
            StatusBarCompat.setStatusBarColor(context as Activity, ContextCompat.getColor(context, R.color.textColorWhite))
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                StatusBarCompat.translucentStatusBar(context as Activity, true)
            else
                StatusBarCompat.translucentStatusBar(context as Activity)
        }
    }

    fun showBadge(context: Context, bottomNavigationView: BottomNavigationView, @IdRes itemId: Int, value: String) {
        val valueBadge: String

        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        val badge = LayoutInflater.from(context).inflate(R.layout.notification_badge, bottomNavigationView, false)
        val text = badge.findViewById<TextView>(R.id.notifications)

        if (value != "0" && value.isNotEmpty()) {
            valueBadge = if (value.toInt() > 999) {
                "999+"
            }else{
                value
            }
            text.text = valueBadge
            itemView.addView(badge)
        }else{
            removeBadge(bottomNavigationView,itemId)
        }
    }

    fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        if (itemView.childCount == 3) { // position menu
            itemView.removeViewAt(2)
        }
    }
}