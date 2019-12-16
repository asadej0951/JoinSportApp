package com.onzon.delivery.user.utils

import android.content.Context
import android.content.res.Configuration
import com.onzon.delivery.user.data.local.Preferences
import java.util.*
import javax.inject.Inject

class LanguagesSetting @Inject constructor(){
    @Inject
    lateinit var mPreferences: Preferences
    fun set(context: Context,ClickCallback: ((String) -> Unit)){

        val locale1 = Locale(mPreferences.getLanguage()?:"EN")
        Locale.setDefault(locale1)
        val config1 = Configuration()
        config1.locale = locale1
        context.resources.updateConfiguration(config1,null)
        ClickCallback.invoke(mPreferences.getLanguage()?:"EN")

    }

}