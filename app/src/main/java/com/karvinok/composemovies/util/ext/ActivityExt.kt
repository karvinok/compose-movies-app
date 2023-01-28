package com.karvinok.composemovies.util.ext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow

fun Activity.hideKeyboard(editText: EditText) {
    try {
        runOnUiThread {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (currentFocus != null && editText.windowToken != null) {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editText.windowToken,
                    0
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.showKeyboard(et: EditText): EditText {
    runOnUiThread {
        et.requestFocus()
        et.postDelayed({
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
    }
    return et
}

fun Activity.openLink(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) })
}

fun Activity.phoneCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    startActivity(intent)
}

fun Context.toast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun Activity.openMarket(intent: Intent) {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${intent.getPackage()}")
            )
        )
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://play.google.com" +
                            "/store/apps/details?id=${intent.getPackage()}"
                )
            )
        )
    }
}

fun <T> AppCompatActivity.observe(state: StateFlow<T?>, function: (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        state.collect { state ->
            state?.let { function.invoke(it) }
        }
    }
}

fun <T> AppCompatActivity.observeNullable(state: StateFlow<T?>, function: (T?) -> Unit) {
    lifecycleScope.launchWhenStarted {
        state.collect { function.invoke(it) }
    }
}
