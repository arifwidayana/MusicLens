package com.arifwidayana.musiclens.di.module

import android.content.Context
import com.arifwidayana.musiclens.data.network.NetworkClient
import com.arifwidayana.musiclens.data.network.service.MusicService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideChucker(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideNetworkClient(chuckerInterceptor: ChuckerInterceptor): NetworkClient {
        return NetworkClient(chuckerInterceptor)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideMusicService(networkClient: NetworkClient): MusicService {
        return networkClient.create()
    }
}