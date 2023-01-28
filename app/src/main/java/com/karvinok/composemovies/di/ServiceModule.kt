package com.karvinok.composemovies.di

import com.karvinok.data.common.CacheManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object ServiceModule : KoinModuleProvider {
    override fun get() = module {
        single { CacheManager(androidContext(), get()) }
    }
}