package com.arifwidayana.musiclens

import android.app.Application
import com.arifwidayana.musiclens.di.MusicLensModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MusicLensApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@MusicLensApp)
            modules(
                MusicLensModule.getModules()
            )
        }
    }
}