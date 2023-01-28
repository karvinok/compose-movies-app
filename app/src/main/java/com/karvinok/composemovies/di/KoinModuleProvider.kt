package com.karvinok.composemovies.di

import org.koin.core.module.Module

interface KoinModuleProvider {
    fun get(): Module
}