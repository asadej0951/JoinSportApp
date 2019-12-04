package com.onzon.delivery.user.di.module

import com.onzon.delivery.BuildConfig
import com.onzon.delivery.user.data.Constants
import com.onzon.delivery.user.data.rest.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.apply {
            connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        }

        httpClient.addInterceptor {
            val original: Request = it.request()
            val request: Request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build()
            return@addInterceptor it.proceed(request)
        }
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(): APIService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttp())
            .build()
            .create(APIService::class.java)
    }
}