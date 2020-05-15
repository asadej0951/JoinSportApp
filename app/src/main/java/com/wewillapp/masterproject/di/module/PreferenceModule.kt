package com.wewillapp.masterproject.di.module


import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.wewillapp.masterproject.data.local.Preferences
import com.wewillapp.masterproject.utils.TokenExpired
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Singleton
    @Provides
    fun provideUserPreference(application: Application): Preferences {
        return Preferences(application)
    }

    @Singleton
    @Provides
    fun provideTokenExpired(application: Application): TokenExpired {
        return TokenExpired(application)
    }

}