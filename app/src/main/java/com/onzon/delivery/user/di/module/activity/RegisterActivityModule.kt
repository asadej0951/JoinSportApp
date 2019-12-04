package com.onzon.delivery.user.di.module.activity


import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import com.onzon.delivery.user.view.register.RegisterActivity

@Module
class RegisterActivityModule {

    @Provides
    fun provideRegister(activity: RegisterActivity): FragmentActivity {
        return activity
    }
}