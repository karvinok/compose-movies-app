package com.karvinok.composemovies.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface CoroutineRunner {

    val parentJob: Job
    val scope: CoroutineScope get() = CoroutineScope(Dispatchers.Default + parentJob)

    fun <P> doOnBackground(showPreloader: Boolean = false, doOnAsyncBlock: suspend CoroutineScope.() -> P): Job {
        return doCoroutineWork(doOnAsyncBlock, Dispatchers.IO) {}
    }

    fun <P> doOnUI(doOnAsyncBlock: suspend CoroutineScope.() -> P): Job {
        return doCoroutineWork(doOnAsyncBlock, Dispatchers.Main) {}
    }

    fun <P> doOnDefault(doOnAsyncBlock: suspend CoroutineScope.() -> P): Job {
        return doCoroutineWork(doOnAsyncBlock, Dispatchers.Default) {}
    }

    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        context: CoroutineContext,
        crossinline doOnCompleteBlock: suspend () -> Unit
    ): Job {
        return scope.launch(context) {
            doOnAsyncBlock.invoke(this)
            doOnCompleteBlock.invoke()
        }
    }
}