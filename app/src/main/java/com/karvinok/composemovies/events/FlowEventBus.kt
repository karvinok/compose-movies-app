package com.karvinok.composemovies.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest

abstract class FlowEventBus<T> {
    private val _events = MutableSharedFlow<T>(replay = 1) // private mutable shared flow, triggered only once

    suspend fun post(event: T) {
        _events.emit(event) // suspends until all subscribers receive it
    }

    suspend fun listen(function: suspend (T) -> Unit) {
        _events.asSharedFlow().collectLatest {
            function.invoke(it)
            _events.resetReplayCache()
        }
    }
}
