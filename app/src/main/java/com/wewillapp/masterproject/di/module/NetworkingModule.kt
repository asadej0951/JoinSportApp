package com.wewillapp.masterproject.di.module

import com.wewillapp.masterproject.BuildConfig
import com.wewillapp.masterproject.data.Constants
import com.wewillapp.masterproject.data.rest.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class NetworkingModule {

    @Provides
    fun providesBaseUrl(): String {
        return BuildConfig.SERVER_URL
    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRxJava2CallFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            val original: Request = it.request()
            val request: Request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build()
            return@addInterceptor it.proceed(request)
        }.addInterceptor(loggingInterceptor)
//            .addInterceptor(ConnectivityInterceptor())
            .connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory,
        rxJava2Call: RxJava2CallAdapterFactory
    ): APIService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(rxJava2Call)
            .build().create(APIService::class.java)
    }
}
