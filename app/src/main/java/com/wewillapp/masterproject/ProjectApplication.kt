package com.wewillapp.masterproject

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDex
import com.wewillapp.masterproject.di.AppInjector
import com.wewillapp.masterproject.util.TypefaceUtil
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class ProjectApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
        AppInjector.init(this)
        onSetTypeface()
    }


    private fun onSetTypeface() {
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/DB OZONE X.ttf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/quark_bold.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/quark_light.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/SF-Pro-Text-Bold.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/SF-Pro-Text-Light.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/SF-Pro-Text-Medium.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/SF-Pro-Text-Regular.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/SF-Pro-Text-Semibold.otf")
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector
}