package com.wewillapp.masterproject

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDex
import com.wewillapp.masterproject.di.AppInjector
import com.wewillapp.masterproject.utils.TypefaceUtil
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
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector
}