package com.wewillapp.masterproject.di.module


import com.wewillapp.masterproject.data.rest.APIService
import com.wewillapp.masterproject.data.rest.OkHttpClientBuilder
import com.wewillapp.masterproject.data.rest.RetrofitBuilder
import org.koin.dsl.module

val networkModule = module {

    single { RetrofitBuilder }

    single<APIService> { get<RetrofitBuilder>().build(
        OkHttpClientBuilder.getUrlServer()) }
}
