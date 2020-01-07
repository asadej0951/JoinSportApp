package com.wewillapp.masterproject.di.module.activity



import androidx.fragment.app.FragmentActivity
import com.wewillapp.masterproject.view.register.RegisterActivity
import dagger.Module
import dagger.Provides

@Module
class RegisterActivityModule {

    @Provides
    fun provideRegister(activity: RegisterActivity): FragmentActivity {
        return activity
    }
}