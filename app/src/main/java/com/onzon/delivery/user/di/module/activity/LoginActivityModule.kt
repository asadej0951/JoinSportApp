package com.onzon.delivery.user.di.module.activity

import androidx.fragment.app.FragmentActivity
import com.onzon.delivery.user.view.login.LoginActivity
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {

    @Provides
    fun provideLoginActivity(activity: LoginActivity): FragmentActivity {
        return activity
    }
}