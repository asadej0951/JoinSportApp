package com.wewillapp.masterproject.di.component

import android.app.Application
import com.wewillapp.masterproject.ProjectApplication
import com.wewillapp.masterproject.di.module.ActivityModule
import com.wewillapp.masterproject.di.module.DataModule
import com.wewillapp.masterproject.di.module.PreferenceModule
import com.wewillapp.masterproject.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PreferenceModule::class,
        ActivityModule::class,
        DataModule::class,
        ViewModelModule::class
    ])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(awesomeApplication: ProjectApplication)
}