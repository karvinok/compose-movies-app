package com.karvinok.composemovies.util.ext

import android.os.Handler
import android.os.Looper

fun postDelayed(delayMs: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, delayMs)
}