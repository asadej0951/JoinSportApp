package com.wewillapp.masterproject.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateManage {
    private const val DATE_PATTERN = "EEE dd MMMM yyyy"
    private const val LANGUAGE_KEY = "th"

    @SuppressLint("SimpleDateFormat")
    fun setFormatDate(strCurrentDate: String): String {
        var dateFormat = SimpleDateFormat(DATE_PATTERN)
        val newDate = dateFormat.parse(strCurrentDate)
        dateFormat = SimpleDateFormat("dd MMM yyyy")
        return dateFormat.format(newDate!!)
    }

    fun getDateCurrent(): String {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString()
    }

    fun timeStampToDateFormat(timeLong: Long): String {
        val formatter = SimpleDateFormat(DATE_PATTERN, Locale(LANGUAGE_KEY))
        return formatter.format(timeLong * 1000)
    }
}