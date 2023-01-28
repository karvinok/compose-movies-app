package com.karvinok.composemovies

import android.app.Application
import com.karvinok.composemovies.di.*
import com.karvinok.composemovies.util.ext.timber
import com.karvinok.data.di.NetworkModule
import com.karvinok.data.di.RepositoryModule
import com.karvinok.data.common.AppConfig
import com.karvinok.data.di.ApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogs()
        initKoin(createAppConfig())
    }

    private fun initKoin(config: AppConfig) {
        startKoin {
            androidContext(this@App)
            modules(
                AppModule.get(config), ServiceModule.get(), NetworkModule.get(),
                ApiModule.get(), RepositoryModule.get(), VMModule.get(), EventBusModule.get()
            )
        }
    }

    private fun initLogs() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        BuildConfig::class.java.declaredFields.forEach {
            timber(it.name + ": " + it.get(it.type))
        }
    }

    private fun createAppConfig() = AppConfig(
        baseUrl = BuildConfig.BASE_URL,
        debug = BuildConfig.DEBUG
    )
}
