package com.wewillapp.masterproject

import android.app.Application
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
    }
}
