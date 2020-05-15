package com.wewillapp.masterproject.utils

import android.content.Context
import android.content.res.Configuration
import com.wewillapp.masterproject.data.local.Preferences
import java.util.*
import javax.inject.Inject

class LanguagesSetting @Inject constructor(var mPreferences: Preferences) {

    fun set(context: Context, clickCallback: ((String) -> Unit)) {

        val locale1 = Locale(mPreferences.getLanguage() ?: "EN")
        Locale.setDefault(locale1)
        val config1 = Configuration()
        config1.locale = locale1
        context.resources.updateConfiguration(config1, null)
        clickCallback.invoke(mPreferences.getLanguage() ?: "EN")

    }

}