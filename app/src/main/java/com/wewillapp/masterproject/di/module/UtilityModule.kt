package com.wewillapp.masterproject.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.utils.CheckPermission
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import com.wewillapp.masterproject.utils.googleMap.MapUtils
import com.wewillapp.masterproject.utils.imageManagement.ConvertUriToFile
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val utilityModule = module {

    single { Preferences(androidApplication()) }

    factory { (activity: FragmentActivity) -> DialogPresenter(activity) }

    single { MapUtils(androidApplication()) }

    single { TokenExpired(androidApplication()) }

    single { Utils(androidApplication(), get()) }

    factory { (activity: AppCompatActivity) ->
        CheckPermission(activity, ConvertUriToFile(androidApplication()))
    }
}


