package com.karvinok.composemovies.util.ext

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.observe(state: StateFlow<T?>, function: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collect { state ->
                state?.let { function.invoke(it) }
            }
        }
    }
}

fun <T> Fragment.observe(state: Flow<T?>, function: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collectLatest{ state ->
                state?.let { function.invoke(it) }
            }
        }
    }
}

fun <T> Fragment.observeNullable(state: StateFlow<T?>, function: (T?) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collect { function.invoke(it) }
        }
    }
}

val Fragment.firstChildFragment: Fragment?
    get() = childFragmentManager.fragments.firstOrNull()

val Fragment.lastChildFragment: Fragment?
    get() = childFragmentManager.fragments.lastOrNull()

fun Fragment.toast(@StringRes res: Int) {
    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}