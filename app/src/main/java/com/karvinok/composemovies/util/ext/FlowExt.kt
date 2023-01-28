package com.karvinok.composemovies.util.ext

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Allows to set value even if it's the same as previous one
 */
fun <T> MutableStateFlow<T?>.postReplicableValue(newValue: T?) {
    if (value == newValue) {
        value = null
    }
    value = newValue
}

fun <T> MutableStateFlow<T?>.postSingleValue(newValue: T?) {
    value = newValue
    value = null
}