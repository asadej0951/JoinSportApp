package com.wewillapp.masterproject.utils

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.wewillapp.masterproject.R
import java.math.BigDecimal
import java.text.DecimalFormat

class ThousandNumberEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {


    companion object {
        private const val MAX_LENGTH = 10
        private const val MAX_DECIMAL = 3
    }

    init {
        init()
    }

    private fun init() {
        addTextChangedListener(
            ThousandNumberTextWatcher(
                this
            )
        )
        inputType = InputType.TYPE_CLASS_NUMBER
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX_LENGTH))
        //setHint("0");
    }

    private class ThousandNumberTextWatcher internal constructor(private val mEditText: EditText) :
        TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            val originalString = editable.toString()
            val cleanString = originalString.replace("[,]".toRegex(), "")
            if (cleanString.isEmpty()) {
                return
            }
            val formattedString = getFormatString(cleanString)

            mEditText.removeTextChangedListener(this)
            mEditText.setText(formattedString)
            mEditText.setSelection(mEditText.text.length)
            mEditText.addTextChangedListener(this)
        }

        /**
         * Return the format string
         */
        private fun getFormatString(cleanString: String): String {
            return if (cleanString.contains(".")) {
                formatDecimal(cleanString)
            } else {
                formatInteger(cleanString)
            }
        }

        private fun formatInteger(str: String): String {
            val parsed = BigDecimal(str)
            val formatter: DecimalFormat
            formatter = DecimalFormat("#,###")
            return formatter.format(parsed)
        }

        private fun formatDecimal(str: String): String {
            if (str == ".") {
                return "."
            }
            val parsed = BigDecimal(str)
            val formatter = DecimalFormat("#,###." + getDecimalPattern(str))
            //example patter #,###.00
            return formatter.format(parsed)
        }


        private fun getDecimalPattern(str: String): String {
            val decimalCount = str.length - 1 - str.indexOf(".")
            val decimalPattern = StringBuilder()
            var i = 0
            while (i < decimalCount && i < MAX_DECIMAL) {
                decimalPattern.append("0")
                i++
            }
            return decimalPattern.toString()
        }
    }
}
