package com.wewillapp.masterproject.di.module.activity

import androidx.fragment.app.FragmentActivity
import com.wewillapp.masterproject.view.login.LoginActivity
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {

    @Provides
    fun provideLoginActivity(activity: LoginActivity): FragmentActivity {
        return activity
    }
}