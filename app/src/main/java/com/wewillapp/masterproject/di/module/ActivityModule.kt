package com.wewillapp.masterproject.di.module

import com.wewillapp.masterproject.di.module.activity.LoginActivityModule
import com.wewillapp.masterproject.di.module.activity.MainActivityModule
import com.wewillapp.masterproject.di.module.activity.RegisterActivityModule
import com.wewillapp.masterproject.di.module.activity.SpashActivityModule
import com.wewillapp.masterproject.di.module.fragment.FragmentModule
import com.wewillapp.masterproject.view.login.LoginActivity
import com.wewillapp.masterproject.view.main.MainActivity
import com.wewillapp.masterproject.view.register.RegisterActivity
import com.wewillapp.masterproject.view.splashScreen.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [SpashActivityModule::class])
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [RegisterActivityModule::class])
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class,(FragmentModule::class)])
    abstract fun contributeMainActivity(): MainActivity
}