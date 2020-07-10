package com.wewillapp.masterproject.utils.watcher

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher

class PhoneNumberTextWatcherAdapter(private var field: (String) -> Unit) : TextWatcher {

    private var isInEditMode = false
    private var current = ""
    private var tmp = ""

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (s.toString() != current) {
            val userInput = s.toString().replace("[^\\d]".toRegex(), "")
            if (userInput.length <= 10) {
                val sb = StringBuilder()
                for (i in userInput.indices) {
                    if (i % 3 == 0 && i <= 6) {
                        sb.append(" ")
                    }
                    sb.append(userInput[i])
                }
                current = sb.toString()

                s.filters = arrayOfNulls<InputFilter>(0)
            }
            s.replace(0, s.length, current, 0, current.length)
            setText(s.toString())
        }
    }

    fun setText(s: String) {
        if (tmp != s) {
            isInEditMode = true
            field.invoke(s)
            tmp = s
            isInEditMode = false
        }
    }
}
