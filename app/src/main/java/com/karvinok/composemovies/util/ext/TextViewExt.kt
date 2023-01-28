package com.karvinok.composemovies.util.ext

import android.animation.ValueAnimator
import android.content.Context
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor

fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    clearFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.animateTextCount(amount: Int, duration: Long = 1000L) {
    ValueAnimator.ofInt(0, amount).apply {
        this.interpolator = DecelerateInterpolator()
        this.duration = duration
        addUpdateListener { animation ->
            text = animation.animatedValue.toString()
        }
    }.start()
}

fun TextView.animateTextColor(color: Int, duration: Long = 300) {
    ValueAnimator.ofArgb(textColors.defaultColor, getColor(context, color)).apply {
        setDuration(duration)
        interpolator = LinearInterpolator()
        addUpdateListener { setTextColor(animatedValue as Int) }
    }.start()
}