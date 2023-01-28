package com.karvinok.data.di

import com.karvinok.data.repository.MoviesRepository
import com.karvinok.data.repository.MoviesRepositoryImpl
import org.koin.dsl.module

object RepositoryModule : KoinModuleProvider {
    override fun get() = module {
        single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }
    }
}
