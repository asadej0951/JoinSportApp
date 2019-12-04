package com.onzon.delivery.user.di.component

import android.app.Application
import com.onzon.delivery.user.ProjectApplication
import com.onzon.delivery.user.di.module.ActivityModule
import com.onzon.delivery.user.di.module.DataModule
import com.onzon.delivery.user.di.module.PreferenceModule
import com.onzon.delivery.user.di.module.ViewModelModule
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