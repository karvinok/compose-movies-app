package com.karvinok.data.di

import com.google.gson.Gson
import com.karvinok.data.remote.MoviesApi
import com.karvinok.data.common.AppConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule : KoinModuleProvider {

    override fun get() = module {
        single { Gson() }
        single { provideDefaultHttpClient(get()) }
        single { provideRetrofit(get(), get()) }
        single { provideApi(get(), MoviesApi::class.java) }
    }

    private fun <T> provideApi(
        retrofit: Retrofit,
        type: Class<T>
    ) = retrofit.create(type)

    private fun provideRetrofit(client: OkHttpClient, appConfig: AppConfig) = Retrofit.Builder()
        .baseUrl(appConfig.baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideDefaultHttpClient(config: AppConfig) = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(provideLoggingInterceptor(config.debug))
        .build()

    private fun provideLoggingInterceptor(debug: Boolean) =
        HttpLoggingInterceptor().apply {
            level = if (debug) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
}
