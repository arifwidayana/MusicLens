package com.arifwidayana.musiclens.di.module

import com.arifwidayana.musiclens.data.network.repository.MusicRepository
import com.arifwidayana.musiclens.domain.SearchArtistUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class UseCaseModule {
    @Provides
    @Singleton
    fun provideSearchArtisUseCase(musicRepository: MusicRepository): SearchArtistUseCase {
        return SearchArtistUseCase(musicRepository, Schedulers.io())
    }
}