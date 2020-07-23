package com.wewillapp.masterproject.data.rest

import com.wewillapp.masterproject.BuildConfig
import com.wewillapp.masterproject.data.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientBuilder {

    fun okHttpClient(): OkHttpClient {
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
            .connectTimeout(Constants.TIME_CONNECT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_CONNECT, TimeUnit.SECONDS)
            .build()

    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    fun getUrlServer(): String {
        return if (BuildConfig.DEBUG) Constants.URL_DEV else Constants.URL_PRO
    }
}