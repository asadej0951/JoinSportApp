package com.wewillapp.masterproject.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
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
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class Utils @Inject constructor(private val mPreferences: Preferences) {

    fun getDeviceMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getMetrics(metrics)
        return metrics
    }

    @SuppressLint("DefaultLocale")
    fun setDefaultLanguage(context: Context, language: String) {
        val config = Configuration()
        config.locale = Locale(language.toLowerCase())
        context.resources.updateConfiguration(config, null)
    }


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

    fun phoneNumberFormat(s: String): String {
        val userInput = s.replace("[^\\d]".toRegex(), "")
        if (userInput.length <= 10) {
            val sb = StringBuilder()
            for (i in userInput.indices) {
                if (i % 3 == 0 && i > 0 && i < 9) {
                    sb.append("-")
                } else if (i % 3 == 0 && i > 9)
                    sb.append("-")

                sb.append(userInput[i])
            }
            return sb.toString()
        }
        return s
    }


    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }


    @SuppressLint("SimpleDateFormat")
    fun setFormatData(dateTime: String): String {
        var mTempDate = ""
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss", Locale(
                mPreferences.getLanguage() ?: "EN".toLowerCase(),
                mPreferences.getLanguage() ?: "EN"
            )
        )

        return try {
            val dateInput = dateFormat.parse(dateTime)

            val pattern = "dd/MM/yy, HH.mm"

            val setFormatDataSelected = SimpleDateFormat(
                pattern,
                Locale(
                    mPreferences.getLanguage() ?: "EN".toLowerCase(),
                    mPreferences.getLanguage() ?: "EN"
                )
            )
            mTempDate = setFormatDataSelected.format(dateInput!!) + " น."

            mTempDate
        } catch (ex: Exception) {
            dateTime
        }
    }


    fun getRandomString(): Int {
        val ranDomNumber = (0..10).random()
        val ranDomNumber2 = (0..99).random()
        return (ranDomNumber * ranDomNumber2) * 1000000000
    }


    fun closeKeyborad(context: AppCompatActivity, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setFormatNumber(number: Double): String {
        val mResult: String
        val mCheckDecimal = number.toString().split(".")
        mResult = if (mCheckDecimal.isNotEmpty()) {
            if (mCheckDecimal[1].toDouble() > 0.0) {
                String.format("%,.2f", number)
            } else {
                String.format("%,.0f", number)
            }
        } else {
            String.format("%,.2f", number)
        }
        return mResult
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
        bottomNavigationView: BottomNavigationView, @IdRes itemId: Int,
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

    fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        if (itemView.childCount == 3) { // position menu
            itemView.removeViewAt(2)
        }
    }
}