package com.wewillapp.masterproject.di.module.fragment


import com.wewillapp.masterproject.view.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    // เดี๋ยวจะใส่ Fragment ทุกๆตัวไว้ในนี้เพื่อทำเป็น Dependency

    @ContributesAndroidInjector
    abstract fun contributeOrderMainFragment(): MainFragment
}