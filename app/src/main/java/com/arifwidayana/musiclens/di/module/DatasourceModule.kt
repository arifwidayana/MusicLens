package com.arifwidayana.musiclens.di.module

import com.arifwidayana.musiclens.data.network.datasource.MusicDatasource
import com.arifwidayana.musiclens.data.network.datasource.MusicDatasourceImpl
import com.arifwidayana.musiclens.data.network.service.MusicService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatasourceModule {
    @Provides
    @Singleton
    fun provideMusicDatasource(musicService: MusicService): MusicDatasource {
        return MusicDatasourceImpl(musicService)
    }
}