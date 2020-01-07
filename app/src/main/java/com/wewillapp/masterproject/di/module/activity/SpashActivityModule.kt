package com.wewillapp.masterproject.di.module.activity

import androidx.fragment.app.FragmentActivity
import com.wewillapp.masterproject.view.splashScreen.SplashScreenActivity
import dagger.Module
import dagger.Provides

@Module
class SpashActivityModule {

    @Provides
    fun provideSplashScreenActivity(activity: SplashScreenActivity): FragmentActivity {
        return activity
    }
}