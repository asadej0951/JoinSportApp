package com.wewillapp.masterproject.utils

import android.content.Context
import android.graphics.Typeface

class TypefaceUtil {
    companion object {
        fun overrideFont(context: Context, defaultFontNameToOverride: String, customFontFileNameInAssets: String) {
            try {
                val customFontTypeface = Typeface.createFromAsset(context.assets, customFontFileNameInAssets)

                val defaultFontTypefaceField = Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
                defaultFontTypefaceField.isAccessible = true
                defaultFontTypefaceField.set(null, customFontTypeface)
            } catch (e: Exception) {
                MyLog.e("Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride")
            }
        }
    }
}