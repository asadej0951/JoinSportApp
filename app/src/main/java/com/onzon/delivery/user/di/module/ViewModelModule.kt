package com.onzon.delivery.user.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onzon.delivery.user.di.anotation.ViewModelKey
import com.onzon.delivery.user.utils.ViewModelFactory
import com.onzon.delivery.user.view.login.LoginViewModel
import com.onzon.delivery.user.view.main.MainViewModel
import com.onzon.delivery.user.view.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindProfileViewModel(viewModel: LoginViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRregisterViewModel(viewModel: RegisterViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    // เดี๋ยวจะประกาศ ViewModel ไว้ในนี้ทีหลัง
}