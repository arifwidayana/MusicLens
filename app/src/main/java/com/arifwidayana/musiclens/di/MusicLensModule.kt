package com.arifwidayana.musiclens.di

import com.arifwidayana.musiclens.arch.base.BaseModule
import com.arifwidayana.musiclens.data.network.NetworkClient
import com.arifwidayana.musiclens.data.network.datasource.MusicDatasource
import com.arifwidayana.musiclens.data.network.datasource.MusicDatasourceImpl
import com.arifwidayana.musiclens.data.network.repository.MusicRepository
import com.arifwidayana.musiclens.data.network.repository.MusicRepositoryImpl
import com.arifwidayana.musiclens.data.network.service.MusicService
import com.arifwidayana.musiclens.domain.SearchArtistUseCase
import com.arifwidayana.musiclens.presentation.ui.MainViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * MusicLensModule inject whole function needed
 */
object MusicLensModule : BaseModule {

    private val network = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get()) }
        single<MusicService> { get<NetworkClient>().create() }
    }

    private val datasource = module {
        single<MusicDatasource> { MusicDatasourceImpl(get()) }
    }

    private val repository = module {
        single<MusicRepository> { MusicRepositoryImpl(get()) }
    }

    private val useCase = module {
        single { SearchArtistUseCase(get(), Dispatchers.IO) }
    }

    private val viewModel = module {
        viewModelOf(::MainViewModel)
    }

    private val common = module {
        single { Gson() }
    }

    override fun getModules(): List<Module> =
        listOf(network, datasource, repository, useCase, viewModel, common)
}