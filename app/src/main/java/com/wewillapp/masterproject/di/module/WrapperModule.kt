package com.wewillapp.masterproject.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class WrapperModule {

//    @Provides
//    fun providesPreferences(context: Context): Preferences {
//        return Preferences(context)
//    }
}
