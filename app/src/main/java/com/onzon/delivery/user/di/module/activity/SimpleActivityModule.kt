package com.onzon.delivery.user.di.module.activity


import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

@Module
class SimpleActivityModule {

    @Provides
    fun provideSimple(activity: SimpleActivity): FragmentActivity {
        return activity
    }
}