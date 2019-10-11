package com.wewillapp.masterproject.di.module

import com.wewillapp.masterproject.di.module.activity.LoginActivityModule
import com.wewillapp.masterproject.di.module.activity.MainActivityModule
import com.wewillapp.masterproject.di.module.fragment.FragmentModule
import com.wewillapp.masterproject.view.login.LoginActivity
import com.wewillapp.masterproject.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    // เดี๋ยวจะใส่ Activity ทุกๆตัวไว้ในนี้เพื่อทำเป็น Dependency
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class,(FragmentModule::class)])
    abstract fun contributeMainActivity(): MainActivity
}