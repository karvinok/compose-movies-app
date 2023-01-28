package com.karvinok.composemovies.di

import com.karvinok.composemovies.App
import com.karvinok.data.common.AppConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AppModule : KoinModuleProvider {

    fun get(appConfig: AppConfig) = module {
        single { appConfig }
        loadKoinModules(get())
    }

    override fun get() = module {
        single { androidApplication() as App }
    }
}