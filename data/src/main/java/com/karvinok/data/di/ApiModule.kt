package com.karvinok.data.di

import com.karvinok.data.remote.MoviesApi
import org.koin.dsl.module
import retrofit2.Retrofit

object ApiModule : KoinModuleProvider {
    override fun get() = module {
        single { provideApi(get(), MoviesApi::class.java) }
    }

    private fun <T> provideApi(
        retrofit: Retrofit,
        type: Class<T>
    ) = retrofit.create(type)
}