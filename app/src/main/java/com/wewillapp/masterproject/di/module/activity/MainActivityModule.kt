package com.wewillapp.masterproject.di.module.activity

import androidx.fragment.app.FragmentActivity
import com.wewillapp.masterproject.view.login.LoginActivity
import com.wewillapp.masterproject.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainActivity(activity: MainActivity): FragmentActivity {
        return activity
    }
}