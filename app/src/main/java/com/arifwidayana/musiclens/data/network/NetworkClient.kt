package com.arifwidayana.musiclens.data.network

import com.arifwidayana.musiclens.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * using for inject network every service interfaces in module injection
 * so that make sure this class is singleton
 */
class NetworkClient(
    val chuckerInterceptor: ChuckerInterceptor
) {
    inline fun <reified I> create(): I {
        val authInterceptor = Interceptor {
            val requestBuilder = it.request().newBuilder()
            it.proceed(requestBuilder.build())
        }

        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okhttpClient)
            .build()

        return retrofit.create(I::class.java)
    }
}