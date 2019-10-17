package com.wewillapp.masterproject.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wewillapp.masterproject.utils.ViewModelFactory
import com.wewillapp.masterproject.di.anotation.ViewModelKey
import com.wewillapp.masterproject.view.login.LoginViewModel
import com.wewillapp.masterproject.view.main.MainViewModel
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
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    // เดี๋ยวจะประกาศ ViewModel ไว้ในนี้ทีหลัง
}