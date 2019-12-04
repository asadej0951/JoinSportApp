package com.onzon.delivery.user.di.module

import com.onzon.delivery.user.di.module.activity.LoginActivityModule
import com.onzon.delivery.user.di.module.activity.MainActivityModule
import com.onzon.delivery.user.di.module.activity.RegisterActivityModule
import com.onzon.delivery.user.di.module.activity.SpashActivityModule
import com.onzon.delivery.user.di.module.fragment.FragmentModule
import com.onzon.delivery.user.view.login.LoginActivity
import com.onzon.delivery.user.view.main.MainActivity
import com.onzon.delivery.user.view.register.RegisterActivity
import com.onzon.delivery.user.view.splashScreen.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    // เดี๋ยวจะใส่ Activity ทุกๆตัวไว้ในนี้เพื่อทำเป็น Dependency
    @ContributesAndroidInjector(modules = [SpashActivityModule::class])
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [RegisterActivityModule::class])
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class,(FragmentModule::class)])
    abstract fun contributeMainActivity(): MainActivity
}