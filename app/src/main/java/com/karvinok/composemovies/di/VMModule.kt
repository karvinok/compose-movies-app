package com.karvinok.composemovies.di

import com.karvinok.composemovies.ui.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object VMModule : KoinModuleProvider {
    override fun get() = module {
        viewModel { MainViewModel(get()) }
    }
}