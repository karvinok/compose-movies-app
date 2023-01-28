package com.karvinok.composemovies.util.ext

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context?.getDrawableResource(@DrawableRes resId: Int): Drawable? {
    return this?.let { AppCompatResources.getDrawable(it, resId) }
}

fun Context?.getColorResource(@ColorRes resId: Int): Int {
    return this?.let { ContextCompat.getColor(it, resId) }?: Color.BLACK
}

fun Resources?.getColorResource(@ColorRes resId: Int): Int {
    return this?.let { ResourcesCompat.getColor(it, resId, null) }?: Color.BLACK
}