package com.arifwidayana.musiclens.di.component

import android.app.Application
import com.arifwidayana.musiclens.MusicLensApp
import com.arifwidayana.musiclens.di.module.ActivityModule
import com.arifwidayana.musiclens.di.module.ContextModule
import com.arifwidayana.musiclens.di.module.DatasourceModule
import com.arifwidayana.musiclens.di.module.NetworkModule
import com.arifwidayana.musiclens.di.module.RepositoryModule
import com.arifwidayana.musiclens.di.module.UseCaseModule
import com.arifwidayana.musiclens.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        ActivityModule::class,
        DatasourceModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelModule::class,
        AndroidInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(musicLensApp: MusicLensApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}