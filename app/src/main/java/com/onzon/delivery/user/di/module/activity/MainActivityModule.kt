package com.onzon.delivery.user.di.module.activity

import androidx.fragment.app.FragmentActivity
import com.onzon.delivery.user.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainActivity(activity: MainActivity): FragmentActivity {
        return activity
    }
}