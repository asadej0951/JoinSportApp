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
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/sf_pro_bold.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/sf_pro_light.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/sf_pro_medium.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/sf_pro_regular.otf")
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/sf_pro_semibold.otf")
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector
}