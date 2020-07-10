package com.wewillapp.masterproject.utils

object TextHelper {
    fun isNotEmptyStrings(vararg value: String?): Boolean {
        val list = value.filter { valueSearch ->
            if (valueSearch == null) {
                return false
            }
            valueSearch.isNotBlank()
        }
        return list.size == value.count()
    }
}
