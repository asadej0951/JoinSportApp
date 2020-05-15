package com.wewillapp.masterproject.view.adapter

import com.super_rabbit.wheel_picker.WheelAdapter
import java.util.*

class WPWeekDaysPickerAdapter(private val arrayList: ArrayList<String>) : WheelAdapter {

    override fun getValue(position: Int): String {
        return if (position < arrayList.size && position >= 0) {
            arrayList[position]
        }else
            ""
    }

    override fun getMaxIndex(): Int {
        return arrayList.size-1
    }

    override fun getMinIndex(): Int {
        return 0
    }

    override fun getPosition(vale: String): Int {
        return arrayList.indexOf(vale)
    }

    override fun getTextWithMaximumLength(): String {
        return arrayList[0]
    }
}