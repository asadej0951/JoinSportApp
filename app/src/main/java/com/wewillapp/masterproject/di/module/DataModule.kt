package com.wewillapp.masterproject.di.module

import com.wewillapp.masterproject.BuildConfig
import com.wewillapp.masterproject.data.Constants
import com.wewillapp.masterproject.data.rest.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
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

        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            getHttpLoggingInterceptor()

        return OkHttpClient.Builder().addInterceptor {
            val original: Request = it.request()
            val request: Request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build()
            return@addInterceptor it.proceed(request)
        }.addInterceptor(interceptor)
            .connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()

    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
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