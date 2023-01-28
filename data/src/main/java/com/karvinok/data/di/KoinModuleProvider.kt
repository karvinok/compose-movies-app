package com.karvinok.data.di

import org.koin.core.module.Module

interface KoinModuleProvider {
    fun get(): Module
}
