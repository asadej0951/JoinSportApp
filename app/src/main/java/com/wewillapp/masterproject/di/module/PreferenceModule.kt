package com.wewillapp.masterproject.di.module


import android.app.Application
import com.wewillapp.masterproject.data.local.Preferences
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
}