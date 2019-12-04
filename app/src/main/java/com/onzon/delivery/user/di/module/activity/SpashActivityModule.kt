package com.onzon.delivery.user.di.module.activity

import androidx.fragment.app.FragmentActivity
import com.onzon.delivery.user.view.splashScreen.SplashScreenActivity
import dagger.Module
import dagger.Provides

@Module
class SpashActivityModule {

    @Provides
    fun provideSplashScreenActivity(activity: SplashScreenActivity): FragmentActivity {
        return activity
    }
}