package com.wewillapp.masterproject.utils.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Matcher
import java.util.regex.Pattern

    fun String.isEmailValid(): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }

    fun String.phoneNumberFormat(): String {
        val userInput = this.replace("[^\\d]".toRegex(), "")
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
        return this
    }

    fun getRandomString(): Int {
        val ranDomNumber = (0..10).random()
        val ranDomNumber2 = (0..99).random()
        return (ranDomNumber * ranDomNumber2) * 1000000000
    }

    fun Double.setFormatNumber(): String {
        return String.format("%,.0f", this)
    }

    fun View.showMessage(message: String) {
        val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snack.show()
    }

