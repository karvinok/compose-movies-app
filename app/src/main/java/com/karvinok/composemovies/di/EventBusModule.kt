package com.karvinok.composemovies.di

import com.karvinok.composemovies.events.SomeBus
import org.koin.dsl.module

object EventBusModule : KoinModuleProvider {
    override fun get() = module {
        single { SomeBus() }
    }
}