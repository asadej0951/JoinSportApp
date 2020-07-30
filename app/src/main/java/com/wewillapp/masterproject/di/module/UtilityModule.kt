package com.wewillapp.masterproject.di.module

import androidx.appcompat.app.AppCompatActivity
import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.utils.TokenExpired
import com.wewillapp.masterproject.utils.Utils
import com.wewillapp.masterproject.utils.dialog.DialogPresenter
import com.wewillapp.masterproject.utils.googleMap.MapUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val utilityModule = module {

    single { Preferences(androidApplication()) }

    factory { (activity: AppCompatActivity) -> DialogPresenter(activity) }

    single { MapUtils(androidApplication()) }

    single { TokenExpired(androidApplication()) }

    single { (activity: AppCompatActivity) -> Utils(activity, get()) }

//    factory { (activity: AppCompatActivity) -> DialogPresenter(activity) }
//
//    factory { (activity: AppCompatActivity) -> CheckPermission(activity) }
}
