package com.arifwidayana.musiclens.di.module

import com.arifwidayana.musiclens.data.network.datasource.MusicDatasource
import com.arifwidayana.musiclens.data.network.repository.MusicRepository
import com.arifwidayana.musiclens.data.network.repository.MusicRepositoryImpl
import com.arifwidayana.musiclens.presentation.ui.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMusicRepository(musicDatasource: MusicDatasource): MusicRepository {
        return MusicRepositoryImpl(musicDatasource)
    }
}