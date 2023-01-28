package com.karvinok.composemovies.util.ext

import com.google.gson.Gson
import com.karvinok.composemovies.BuildConfig
import timber.log.Timber

fun Any.timber(string: String? = null, error: Boolean = false) {
    if (BuildConfig.DEBUG) {
        string?.let {
            if (error) Timber.tag(this.javaClass.name).e(string)
            else Timber.tag(this.javaClass.name).d(string)
        }
    }
}

fun Any.timber(any: Any? = null, error: Boolean = false) {
    if (BuildConfig.DEBUG) {
        any?.let {
            try {
                if (error) Timber.tag(this.javaClass.name).e("___ ${Gson().toJson(any)}")
                else Timber.tag(this.javaClass.name).d("___ ${Gson().toJson(any)}")
            } catch (e: java.lang.Exception) {
                timber(e)
            }
        }
    }
}

fun Any.timber(exception: Exception) {
    if (BuildConfig.DEBUG) {
        exception.let {
            Timber.tag(this.javaClass.name).e(exception)
        }
    }
}
