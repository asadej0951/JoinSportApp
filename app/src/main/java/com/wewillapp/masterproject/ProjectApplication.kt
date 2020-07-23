package com.wewillapp.masterproject

import android.app.Application
import androidx.multidex.MultiDex
import com.wewillapp.masterproject.di.module.networkModule
import com.wewillapp.masterproject.di.module.repositoryModule
import com.wewillapp.masterproject.di.module.utilityModule
import com.wewillapp.masterproject.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        startKoin {
            androidContext(this@ProjectApplication)
            modules(arrayListOf(networkModule, utilityModule, repositoryModule, viewModelModule))
            androidLogger()
        }
    }
}
