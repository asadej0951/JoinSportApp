package com.wewillapp.masterproject.di.module.activity


import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import com.wewillapp.masterproject.view.register.RegisterActivity

@Module
class RegisterActivityModule {

    @Provides
    fun provideRegister(activity: RegisterActivity): FragmentActivity {
        return activity
    }
}